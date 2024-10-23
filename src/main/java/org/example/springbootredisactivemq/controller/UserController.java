package org.example.springbootredisactivemq.controller;

import org.example.springbootredisactivemq.model.User;
import org.example.springbootredisactivemq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

//    Create a User Details
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

//    Getting the User Details Using Id
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

//    Getting All the User Details
    @GetMapping
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

//   Update the User Details
    @PutMapping("/{id}")
    public String updateUser(@PathVariable String id, @RequestBody User user) {
       return  userService.updateUser(id, user);
    }

//    Deleting the User Details Using Id
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        return userService.deleteUserById(id);
    }

//    Deleting All the User Details
    @DeleteMapping
    public String deleteAllUsers() {
        return userService.deleteAllUsers();
    }
}