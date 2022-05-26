package com.example.batch_20220525;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class BatchConfiguration extends DefaultBatchConfigurer {

//    @Override
//    public void setDataSource(DataSource dataSource) {
//        DataSourceBuilder.create().build();
//        //return DataSourceBuilder.create().build();
//    }

    @Bean(name="mySqlDataSource2")
    //@Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .build();
    }


}
