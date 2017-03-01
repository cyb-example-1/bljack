package com.cybernetica.bj.server.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Spring boot entry-point
 * @author dmitri
 *
 */
@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@EnableWebMvc
@Import(value={DatabaseConfig.class,HttpSessionConfig.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
