package io.rellyson.playground.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DataSourceConfig {
    private final Logger logger;
    private final Environment environment;

    public DataSourceConfig(Environment environment) {
        this.environment = environment;
        this.logger = LoggerFactory.getLogger(DataSourceConfig.class);
    }

     @Bean
    public DataSource setDataSource() throws SQLException {
         DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
         dataSourceBuilder.driverClassName(this.environment.getProperty("jdbc.driverClassName","org.postgresql.Driver"));
         dataSourceBuilder.url(this.environment.getProperty("jdbc.url"));
         dataSourceBuilder.username(this.environment.getProperty("jdbc.username"));
         dataSourceBuilder.password(this.environment.getProperty("jdbc.password"));
         DataSource dataSource = dataSourceBuilder.build();

         if (dataSource.getConnection().isValid(
                 Integer.parseInt(this.environment.getProperty("connTimeoutSeconds","3")))
         ) {
             this.logger.info("DataSource configured successfully!");
         }

         return dataSource;
     }
}
