package io.rellyson.playground.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class FlywayConfig {
    private final Environment environment;

    private final int MAX_CONNECTION_RETRIES = 3;

    public FlywayConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean(initMethod = "migrate")
    public Flyway setFlyway() {
        return  new Flyway(Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(
                        this.environment.getProperty("jdbc.url"),
                        this.environment.getProperty("jdbc.username"),
                        this.environment.getProperty("jdbc.password")
                )
                .locations("classpath:db/migration")
                .validateMigrationNaming(true)
                .connectRetries(MAX_CONNECTION_RETRIES)
        );
    }
}
