package com.epam.esm.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    private static final String DATABASE_PROPERTIES = "database.properties";
    private static final String DATABASE_URI = "uri";
    private static final String DATABASE_USER = "user";
    private static final String DATABASE_PASS = "pass";
    private static final String DATABASE_DRIVER = "driver";
    private static final Properties properties = new Properties();

    private static final String URI;
    private static final String USER;
    private static final String PASS;
    private static final String DRIVER_CLASS_NAME;

    static {
        try(InputStream inputStream = DatabaseConfig.class.getClassLoader().getResourceAsStream(DATABASE_PROPERTIES)){
            properties.load(inputStream);
            URI = properties.getProperty(DATABASE_URI);
            USER = properties.getProperty(DATABASE_USER);
            PASS = properties.getProperty(DATABASE_PASS);
            DRIVER_CLASS_NAME = properties.getProperty(DATABASE_DRIVER);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File with properties" + DATABASE_PROPERTIES + " not found: " + e);
        } catch (IOException e) {
            throw new RuntimeException("Reading error: " + e);
        }
    }


    @Bean
    public DataSource dateSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUrl(URI);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dateSource());
    }

    @Bean("transactionManager")
    public PlatformTransactionManager getTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
