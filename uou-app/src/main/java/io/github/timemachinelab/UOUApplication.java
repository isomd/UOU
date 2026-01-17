package io.github.timemachinelab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "io.github.timemachinelab" })
public class UOUApplication {
    public static void main(String[] args) {
        SpringApplication.run(UOUApplication.class, args);
    }
}