package com.kovko.dictionary.config;

import com.kovko.dictionary.dto.MiniCard;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spi.DataFormat;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Author: eukovko
 * Date: 6/14/2020
 */
@Configuration
public class CamelConfig {

    @Bean
    DataFormat jacksonDataFormat(){
        return new JacksonDataFormat(MiniCard.class);
    }

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean("postgresDataSource")
    DataSource dataSource(DataSourceProperties dataSourceProperties){
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    DataSourceProperties dataSourceProperties(){
        return new DataSourceProperties();
    }

}
