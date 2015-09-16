package com.elevations.beans;

import org.postgresql.jdbc2.optional.SimpleDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class PostgresqlDB
{
    @Bean
    @ConfigurationProperties( prefix = "spring.datasource" )
    public DataSource dataSource()
    {
        return new SimpleDataSource();
    }
}
