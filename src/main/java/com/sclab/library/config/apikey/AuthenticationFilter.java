package com.sclab.library.config.apikey;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * API Gateway
 */
public class AuthenticationFilter extends GenericFilterBean {
    private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final AuthenticationService authenticationService;

    public AuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(
            jakarta.servlet.ServletRequest request,
            jakarta.servlet.ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        logger.trace("do filter...");
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        final String HTTP_METHOD = ((HttpServletRequest) request).getMethod().toUpperCase();
        final String HTTP_URL = ((HttpServletRequest) request).getRequestURI().toString();
        try {

            // authentication - 401
            ApiEntityDTO apiKeyEntity = authenticationService.getAuthentication((HttpServletRequest) request);
            Authentication authentication = new ApiKeyAuthentication(apiKeyEntity.keyValue(), AuthorityUtils.NO_AUTHORITIES);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // authorization - 403
            if(!isUserAuthorized(apiKeyEntity.userType() ,HTTP_METHOD)){
                httpResponse.setContentType("application/json");
                httpResponse.setStatus(403);
                ForbiddenException forbiddenException = new ForbiddenException(
                        LocalDateTime.now().toString(),
                        403,
                        String.format(
                                "%s do not have permission to access this resource : [%s] %s",
                                apiKeyEntity.userType(),
                                HTTP_METHOD,
                                HTTP_URL
                        ),
                        HTTP_URL
                );
                httpResponse.getWriter().write(forbiddenException.toString());
                logger.error(forbiddenException.toString());
                return;
            }
        } catch (Exception ex) {
            Optional<KnownSecurityException> optionalKnownSecurityException = KnownSecurityException.fromMessage(ex.getMessage());
            int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            if (optionalKnownSecurityException.isPresent()) {
                status = optionalKnownSecurityException.get().getHttpStatus();
            }
            httpResponse.setContentType("application/json");
            httpResponse.setStatus(status);

            UnauthorizedException unauthorizedException = new UnauthorizedException(
                    LocalDateTime.now().toString(),
                    status,
                    ex.getMessage(),
                    ex.getCause().toString(),
                    HTTP_URL,
                    ex.getStackTrace()[0].toString()
            );
            httpResponse.getWriter().write(unauthorizedException.toString());
            logger.error(unauthorizedException.toString());
            return;
        }
        chain.doFilter(request, response);
    }

    /**
     * all user only eligible to use GET call, except ADMIN
     */
    private boolean isUserAuthorized(String userType, String requestMethod){
        System.out.println("isUserAuthorized");
        if(!userType.equalsIgnoreCase("ADMIN") && !requestMethod.equalsIgnoreCase("GET")){
            System.out.println("FALSE");
            return false;
        }
        System.out.println("TRUE");
        return true;
    }

}
