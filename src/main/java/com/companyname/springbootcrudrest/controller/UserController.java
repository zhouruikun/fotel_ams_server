package com.companyname.springbootcrudrest.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.companyname.springbootcrudrest.beans.ResponseCommon;
import com.companyname.springbootcrudrest.beans.UserList;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.companyname.springbootcrudrest.exception.ResourceNotFoundException;
import com.companyname.springbootcrudrest.model.User;
import com.companyname.springbootcrudrest.repository.UserRepository;

@RestController
@RequestMapping("/")
@Api(value="usert Management System", description="Operations pertaining to user in user Management System")
public class UserController {




    @Autowired
    private UserRepository userRepository;


    public String getUsers() {
        return "Hello Spring Security";
    }


    @ApiOperation(value = "View a list of available employees", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/users")
    public ResponseCommon getAllUsers() {
        ResponseCommon res1 = new ResponseCommon();
        UserList list = new UserList();
        List listUsers = userRepository.findAll();
        list.setItems(listUsers);
        list.setTotal(listUsers.size());
        res1.setCode(20000);
        res1.setMessage("success");
        res1.setData(list);
        return res1;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: "+ userId));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users")
    public ResponseCommon createUser(
            @ApiParam(value = "User object store in database table", required = true)
            @Valid @RequestBody User user) {

        ResponseCommon res = new ResponseCommon();
        res.setCode(20000);
        res.setData(userRepository.save(user));
        res.setMessage("User object store in database table");
        return res;
    }

    @PutMapping("/users/{id}")
    public ResponseCommon updateUser(

            @PathVariable(value = "id") Long userId,

            @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: "+ userId));

        ResponseCommon res = new ResponseCommon();
        userDetails.setUpdatedAt(new Date());
        final User updatedUser = userRepository.save(userDetails);
        res.setCode(20000);
        res.setMessage("update success");
        res.setData(ResponseEntity.ok(updatedUser));
        return res;
    }

    @DeleteMapping("/user/{id}")
    public Map<String, Boolean> deleteUser(
            @PathVariable(value = "id") Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: "+ userId));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}