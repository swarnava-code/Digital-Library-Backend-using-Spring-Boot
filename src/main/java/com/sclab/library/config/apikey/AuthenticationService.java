package com.sclab.library.config.apikey;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;

public class AuthenticationService {

    private final ApiKeyService apiKeyService;

    public AuthenticationService(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    private final String API_KEY = "X-API-KEY";

    public ApiEntityDTO getAuthentication(HttpServletRequest request) {
        String apiKeyValue = request.getHeader(API_KEY);
        if (apiKeyValue == null) {
            throw new BadCredentialsException(KnownSecurityException.MISSING_API_KEY.getMessage());
        }
        ApiEntityDTO apiEntityDTO;
        try{
             apiEntityDTO = apiKeyService.getApiKeyEntity(apiKeyValue);
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        String storedApiKey = apiEntityDTO.keyValue();
        if (!apiKeyValue.equals(storedApiKey)) {
            throw new BadCredentialsException(KnownSecurityException.INVALID_API_KEY.getMessage());
        }

        return apiEntityDTO;
    }

}
