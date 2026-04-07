package com.yhj.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * ERP System Application Entry Point.
 * This is the aggregated application that includes all modules.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.yhj.erp")
public class ErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpApplication.class, args);
    }
}