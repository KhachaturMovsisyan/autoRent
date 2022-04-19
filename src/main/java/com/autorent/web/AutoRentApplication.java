package com.autorent.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EntityScan(basePackageClasses = {AutoRentApplication.class})
@EnableAsync
public class AutoRentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoRentApplication.class, args);
    }

}
