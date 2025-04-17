package ru.starter_t1.config;

import org.apache.logging.log4j.Level;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.starter_t1.aspect.LoggingAspect;
import ru.starter_t1.config.util.LogLevelEnum;

@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
@ConditionalOnProperty(prefix = "app.logging", value = "enabled", havingValue = "true", matchIfMissing = true)
public class AutoConfiguration {
    private final ConfigProperties configProperties;

    public AutoConfiguration(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect(logLevel());
    }


    @Bean
    public Level logLevel() {
        LogLevelEnum level = configProperties.getLevel();
        if (level == null) {
            return Level.INFO;
        }
        return Level.getLevel(level.name());
    }
}
