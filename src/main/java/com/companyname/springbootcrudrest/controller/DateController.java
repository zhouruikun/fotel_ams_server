package com.companyname.springbootcrudrest.controller;

import com.companyname.springbootcrudrest.exception.ResourceNotFoundException;
import com.companyname.springbootcrudrest.model.Node;
import com.companyname.springbootcrudrest.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class DateController {
    @GetMapping("/put_data")
    public String putdata(@RequestBody(required = false) String date) {
        //获取到JSONObject
        System.out.println(date);
        return "{\"status\":\"ok\"}\r\n";
    }

}