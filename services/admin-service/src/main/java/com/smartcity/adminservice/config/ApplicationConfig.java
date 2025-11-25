package com.smartcity.adminservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 应用配置类
 * 配置应用信息
 */
@Configuration
@EnableConfigurationProperties
public class ApplicationConfig {

    /**
     * 应用元数据配置
     */
    @Configuration
    @ConfigurationProperties(prefix = "application")
    public static class ApplicationProperties {
        private String name;
        private String version;
        private String description;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
