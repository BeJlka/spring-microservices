package com.bejlka.notificationservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "application")
public class TemplateConfig {
    private Map<String, Template> templates;

    @Data
    public static class Template {

        private String path;
        private String subject;

    }
}
