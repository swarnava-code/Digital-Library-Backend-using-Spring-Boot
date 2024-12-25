package com.sclab.library.config.apikey;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKeyEntity, Long> {
    Optional<ApiKeyEntity> findByUserType(String keyName);
    Optional<ApiKeyEntity> findByKeyValue(String keyName);
}
