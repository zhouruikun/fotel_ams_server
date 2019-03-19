package com.companyname.springbootcrudrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableJpaAuditing
public class SpringbootcrudrestApplication {

    public static void main(String[] args) {


        SpringApplication.run(SpringbootcrudrestApplication.class, args);
    }

}
