package com.companyname.springbootcrudrest;

import com.companyname.springbootcrudrest.utils.SmsAlert;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@SpringBootApplication
@EnableJpaAuditing
public class SpringbootcrudrestApplication {

    public static void main(String[] args) {


        SpringApplication.run(SpringbootcrudrestApplication.class, args);
    }

}
