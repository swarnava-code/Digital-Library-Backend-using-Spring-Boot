package com.sclab.library.config.apikey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKeyEntity, Long> {
    Logger logger = LoggerFactory.getLogger(ApiKeyRepository.class);

    Optional<ApiKeyEntity> findByUserType(String type);

    Optional<ApiKeyEntity> findByKeyValue(String key);

    default Optional<ApiKeyEntity> findByKeyValueWithLogging(String keyValue) {
        logger.info("Fetching API key from DB");
        return findByKeyValue(keyValue);
    }

}
