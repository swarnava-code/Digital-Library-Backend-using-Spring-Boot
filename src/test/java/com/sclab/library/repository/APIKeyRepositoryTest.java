package com.sclab.library.repository;


import com.sclab.library.config.apikey.ApiKeyEntity;
import com.sclab.library.config.apikey.ApiKeyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // create H2 test db by default, if it failed then following annotation will handle
@ActiveProfiles("test") // load the test-specific properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// Replace.AUTO_CONFIGURED / Replace.ANY (for H2 JDBC Driver)
// replace = AutoConfigureTestDatabase.Replace.NONE (for production db, mentioned in property file, after test it will roll back)
public class APIKeyRepositoryTest {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private DataSource dataSource;

    private void printDbDetails() {
        System.out.println("============================================");
        try {
            DatabaseMetaData databaseMetaData = dataSource.getConnection().getMetaData();
            System.out.println("URL: " + databaseMetaData.getURL());
            System.out.println("User Name: " + databaseMetaData.getUserName());
            System.out.println("Driver Name: " + databaseMetaData.getDriverName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("============================================");
    }

    @Test
    public void testFindById__whenIdIsValid() {
        printDbDetails();
        System.out.println("printing...");
        final String KEY_VALUE = "123-456";
        apiKeyRepository.save(new ApiKeyEntity(2L, KEY_VALUE, "DEV", "10"));
        var optAuthor = apiKeyRepository.findByKeyValue(KEY_VALUE);
        assertTrue(optAuthor.isPresent());
    }

}