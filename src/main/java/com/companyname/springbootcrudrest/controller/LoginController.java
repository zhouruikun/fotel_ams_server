package com.companyname.springbootcrudrest.controller;


import com.companyname.springbootcrudrest.beans.*;
import com.companyname.springbootcrudrest.exception.ResourceNotFoundException;
import com.companyname.springbootcrudrest.model.User;
import com.companyname.springbootcrudrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseCommon login(@RequestBody(required = false) String date) {
        //获取到JSONObject
        ResponseCommon res = new ResponseCommon();
        Token token = new Token();
        token.setToken("admin-token");
        res.setCode(20000);
        res.setData(token);
        return res;
    }

    @GetMapping("/info")
    public ResponseCommon info(@RequestBody(required = false) String date) throws ResourceNotFoundException {
        //获取到JSONObject
        //获取到JSONObject
        ResponseCommon res = new ResponseCommon();

        long userId = 1;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
        res.setCode(20000);
        res.setData(user);
        return res;
    }

    @PostMapping("/logout")
        public ResponseCommon logout(HttpServletRequest request) {
        //获取到JSONObject

        System.out.println(request.getHeader("X-Token"));
        ResponseCommon res = new ResponseCommon();
        res.setCode(20000);
        res.setData("success");
        return res;
    }
}