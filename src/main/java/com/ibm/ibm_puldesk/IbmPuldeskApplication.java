package com.ibm.ibm_puldesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"Controller", "Services", "Repository", "Model", "DT0"})
@EnableJpaRepositories(basePackages = "Repository")
@EntityScan(basePackages = "Model")
public class IbmPuldeskApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(IbmPuldeskApplication.class, args);
        try {
            Runtime.getRuntime().exec("cmd /c start http://localhost:8080");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}