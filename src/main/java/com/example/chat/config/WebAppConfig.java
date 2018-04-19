package com.example.chat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author liuyiqian
 */
@Component
@ConfigurationProperties(prefix = "file")
@PropertySource(value = "classpath:/web-app.properties")
public class WebAppConfig {

    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
