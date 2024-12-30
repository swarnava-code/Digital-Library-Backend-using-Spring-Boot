package com.sclab.library.config.apikey;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Cacheable(cacheNames = "apiKeyEntities", key = "#keyName")
    public String getApiKey(String keyName) {
        Optional<ApiKeyEntity> optionalApiKey = apiKeyRepository.findByKeyValueWithLogging(keyName);
        if (optionalApiKey == null || !optionalApiKey.isPresent()) {
            throw new IllegalArgumentException(KnownSecurityException.API_KEY_NOT_FOUND.getMessage());
        }
        return optionalApiKey.get().getKeyValue();
    }
}
