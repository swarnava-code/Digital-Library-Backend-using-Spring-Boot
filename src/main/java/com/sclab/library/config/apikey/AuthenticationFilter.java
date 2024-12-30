package com.sclab.library.config.apikey;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Optional;

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

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            Authentication authentication = authenticationService.getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            Optional<KnownSecurityException> optionalKnownSecurityException = KnownSecurityException.fromMessage(ex.getMessage());
            int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            if (optionalKnownSecurityException.isPresent()) {
                status = optionalKnownSecurityException.get().getHttpStatus();
            }
            httpResponse.setContentType("application/json");
            httpResponse.setStatus(status);
            String jsonResponse = String.format(
                    "{\"timestamp\":\"%s\",\"status\":%d,\"message\":\"%s\",\"path\":\"%s\"}",
                    java.time.LocalDateTime.now(),
                    status,
                    ex.getMessage(),
                    ((HttpServletRequest) request).getRequestURI()
            );
            // Write JSON response
            httpResponse.getWriter().write(jsonResponse);
            logger.error(jsonResponse);
            return; // Stop further processing
        }
        chain.doFilter(request, response);
    }

}
