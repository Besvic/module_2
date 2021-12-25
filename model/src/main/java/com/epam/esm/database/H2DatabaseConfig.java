package com.epam.esm.database;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * The type H 2 database config.
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
@ComponentScan("com.epam.esm")
@Profile({"test"})
public class H2DatabaseConfig {
    @Value("${url}")
    private String URI;
    @Value("${user}")
    private String USER;
    @Value("${password}")
    private String PASS;
    @Value("${driverClassName}")
    private String DRIVER_CLASS_NAME;

    /**
     * Date source data source.
     *
     * @return the data source
     */
    @Bean
    public DataSource dateSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUrl(URI);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);
        return dataSource;
    }

    /**
     * Jdbc template jdbc template.
     *
     * @return the jdbc template
     */
    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dateSource());
    }

    /**
     * Gets transaction manager.
     *
     * @param dataSource the data source
     * @return the transaction manager
     */
    @Bean("transactionManager")
    public PlatformTransactionManager getTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
