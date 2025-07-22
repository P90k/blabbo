package com.blabbo.app.blabbo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.blabbo.app.blabbo.service",
                               "com.blabbo.app.blabbo.redis"})
public class AppConfig {
}
