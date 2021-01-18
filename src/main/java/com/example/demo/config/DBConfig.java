package com.example.demo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class DBConfig {
    @Bean
    public HikariConfig hikariConfig() {
        var config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/eCare");
        config.setUsername("postgres");
        config.setPassword("vogula");

        config.setMaximumPoolSize(2);
        config.setConnectionTestQuery("select 1");

        return config;
    }

    @Bean
    public DataSource getDataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

//    @Bean
//    public SpringLiquibase liquibase(DataSource dataSource) {
//        SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setChangeLog("classpath:liquibase-changeLog.yaml");
//        liquibase.setDataSource(dataSource);
//        return liquibase;
//    }
}
