package org.arsh.authy.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.arsh.authy.app", "org.arsh.authy.web"})
public class AuthyApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthyApplication.class, args);
    }
}