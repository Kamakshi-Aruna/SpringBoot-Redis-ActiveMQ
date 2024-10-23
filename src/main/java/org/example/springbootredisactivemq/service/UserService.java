package org.example.springbootredisactivemq.service;

import org.example.springbootredisactivemq.model.User;
import org.example.springbootredisactivemq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

//    Send message to ActiveMQ
    public void sendUserMessage(String message) {
        jmsTemplate.convertAndSend("userQueue", message);
    }

//    Create a User Details
    public User createUser(User user) {
        User createdUser=userRepository.save(user);
       sendUserMessage("User created: " + createdUser.getName() + " (ID: " + createdUser.getId() + ")");
       return createdUser;
    }

//    Getting the User Details Using Id
    @Cacheable(value = "User", key = "#id")
    public Optional<User> getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            sendUserMessage("User fetched: " + user.get().getName() + " (ID: " + id + ")");
        } else {
            sendUserMessage("User with ID " + id + " not found");
        }
        return user;
    }

//    Getting All the User Details
    public Iterable<User> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        if (!users.iterator().hasNext()) {
            sendUserMessage("Users not found");
        } else {
            sendUserMessage("All users fetched");
        }
        return users;
    }


//    Update the User Details
    public String updateUser(String id, User user) {
        Optional<User> op = userRepository.findById(id);
        if (op.isPresent()) {
            User existingUser = op.get();
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            userRepository.save(existingUser);
            sendUserMessage("User updated: " + existingUser.getName() + " (ID: " + id + ")");
            return "Data Updated successfully!!";
        } else {
            sendUserMessage("User with ID " + id + " not found for update");
            return "User Not Found!!";
        }
    }

//    Delete the User Details Using Id
    public String deleteUserById(String id) {
        Optional<User> op = userRepository.findById(id);
        if (op.isPresent()) {
            userRepository.deleteById(id);
            sendUserMessage("User deleted: (ID: " + id + ")");
            return "Data Deleted Successfully!!";
        } else {
            sendUserMessage("User with ID " + id + " not found for deletion");
            return "No Data Found!!";
        }
    }

//    Deleting All the User Details
    public String deleteAllUsers() {
        Iterable<User> users = userRepository.findAll();
        if (!users.iterator().hasNext()) {
            sendUserMessage("No users found to delete");
            return "No Data Found!!";
        } else {
            userRepository.deleteAll();
            sendUserMessage("All users deleted");
            return "Data Deleted Successfully!!";
        }
    }
}