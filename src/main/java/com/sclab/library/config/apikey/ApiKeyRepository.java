package com.sclab.library.config.apikey;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKeyEntity, Long> {
    Optional<ApiKeyEntity> findByUserType(String keyName);

//    @Cacheable(cacheNames = "apiKeyEntities", key = "#id")
    Optional<ApiKeyEntity> findByKeyValue(String keyName);
}
