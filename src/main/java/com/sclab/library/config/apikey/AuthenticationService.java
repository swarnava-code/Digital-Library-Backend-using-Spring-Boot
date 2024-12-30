package com.sclab.library.config.apikey;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

public class AuthenticationService {

    private final ApiKeyService apiKeyService;

    public AuthenticationService(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    private final String API_KEY = "X-API-KEY";

    public Authentication getAuthentication(HttpServletRequest request) {
        String apiKeyValue = request.getHeader(API_KEY);
        if (apiKeyValue == null) {
            throw new BadCredentialsException(KnownSecurityException.MISSING_API_KEY.getMessage());
        }

        String storedApiKey = apiKeyService.getApiKey(apiKeyValue);
        if (!apiKeyValue.equals(storedApiKey)) {
            throw new BadCredentialsException(KnownSecurityException.INVALID_API_KEY.getMessage());
        }

        return new ApiKeyAuthentication(apiKeyValue, AuthorityUtils.NO_AUTHORITIES);
    }

}
