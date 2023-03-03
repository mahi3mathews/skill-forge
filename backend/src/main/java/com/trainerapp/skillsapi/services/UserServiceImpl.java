package com.trainerapp.skillsapi.services;

import com.trainerapp.skillsapi.models.User;
import com.trainerapp.skillsapi.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

/**
 * UserServiceImpl is the implementation class for the UserService interface.
 * It represents the model in the MVC architecture.
 * It represents the service layer in the layered architecture.
 * It implements the creator pattern of the GRASP patterns as it handle creation and updates of the user object.
 * It implements the Don’t Talk to Strangers pattern of the GRASP patterns as it ensures the implementation of UserRepository is hidden from external entities.
 * It implements the information expert as it has access to user repository and can create, update and retrieve users.
 */
@Service
public class UserServiceImpl implements UserService{


    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AttendeeService attendeeService;
    @Override
    public User registerUser(User user, String userName) throws FileAlreadyExistsException {
        System.out.println("username "+ user.getUserName());
        Optional<User> existingUser = userRepository.findByUserName(user.getUserName());
        System.out.println("existing user "+ existingUser.isPresent() );
        if(existingUser.isPresent()){
            throw new FileAlreadyExistsException("User already exists.");
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("ATTENDEE");
            user.setCreatedOn( new Date());
            user.setUpdatedOn(new Date());
            user.setUserId(UUID.randomUUID().toString());
            attendeeService.createdAttendee(user.getUserId());
            return userRepository.insert(user);
        }
    }

    @Override
    public User loginUser(String userName, String password) throws FileNotFoundException, AccessDeniedException {
        Optional<User> user1 =  userRepository.findByUserName(userName);
        if(user1.isEmpty()){
            throw new FileNotFoundException("User not found.");}
        else {
            User userObj = user1.get();
            if (passwordEncoder.matches(password, userObj.getPassword())) {
                return userObj;
            }else{
                throw new AccessDeniedException("Incorrect userName or password.");
            }
        }
    }

    @Override
    public User getUser(String userId) throws FileNotFoundException {
        User user = userRepository.findByUserId(userId);
        if(user==null){
            throw new FileNotFoundException("User does not exist.");
        }
        else return user;
    }

    @Override
    public List getAllUsers(){
        return userRepository.findAll();

    }

}
