package com.example.autorent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan(basePackageClasses = {AutoRentApplication.class})
public class AutoRentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoRentApplication.class, args);
    }

}
