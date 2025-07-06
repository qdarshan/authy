package org.arsh.authy.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Value("${app.base-domain}")
    private String baseDomain;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String localPattern = "http://localhost:[*]";
                String subdomainPattern = "http://*." + baseDomain + ":[*]";
                String secureSubdomainPattern = "https://*." + baseDomain + ":[*]";
                String baseDomainPattern = "http://" + baseDomain + ":[*]";
                String secureBaseDomainPattern = "https://" + baseDomain + ":[*]";

                registry.addMapping("/api/**")
                        .allowedOrigins(
                                localPattern,
                                subdomainPattern,
                                secureSubdomainPattern,
                                baseDomainPattern,
                                secureBaseDomainPattern
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
