package ru.starter_t1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import ru.starter_t1.config.util.LogLevelEnum;


@ConfigurationProperties(prefix = "app.logging")
public class ConfigProperties {
    private final LogLevelEnum level;
    private final Boolean enabled;

    @ConstructorBinding
    public ConfigProperties(LogLevelEnum level, Boolean enabled) {
        this.level = level;
        this.enabled = enabled;
    }

    public LogLevelEnum getLevel() {
        return level;
    }

    public Boolean isEnabled() {
        return enabled;
    }
}
