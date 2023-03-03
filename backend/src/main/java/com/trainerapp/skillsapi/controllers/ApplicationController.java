package com.trainerapp.skillsapi.controllers;

import com.trainerapp.skillsapi.models.Training;
import com.trainerapp.skillsapi.models.User;
import com.trainerapp.skillsapi.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

/**
 * APPLICATION controller is used to handle all API REST calls from all users to the system including the admin
 * It represents the presentation layer.
 * It implements the GRASP Controller pattern as it takes the request from the user in the presentation layer in the application and communicates with the appropriate service layer that uses the DAO layer to update or read the database
 * It represents the controller from the MVC architecture by managing the user input and returning the output from the database to be displayed.
 * It implements the creator pattern of GRASP patterns as it creates new users.
 */
@RestController
@RequestMapping("api/v1/skillforge")
@CrossOrigin(origins = "http://localhost:3000")
public class ApplicationController {
    /**
     * User service is used to login and register a user to the system.
     * @Qualifier is used to mention which implementation to access.
     * TrainingService interface is used with the AdminTrainingServiceImpl implementation.
     */

    @Autowired
    UserService userService;
    @Autowired
    @Qualifier("adminTrainingService")
    TrainingService trainingService;

    /**
     * POST mapping to log in the user with provided details
     * @param user the User object which consists of password and userName
     * @return a user object or error message
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginAttendee(@RequestBody User user){
        try {
            System.out.println( user.getUserName() +" user name");
            return new  ResponseEntity<>( userService.loginUser(user.getUserName(), user.getPassword()), HttpStatus.CREATED);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

    /**
     * POST mapping to add a user to the system
     * @param user is the User object to be added to the sytem
     * @return user created in the system or error message based on operations.
     */
    @PostMapping("/register")
    public ResponseEntity<?> createAttendee(@RequestBody User user){
        try{
            return new ResponseEntity<>(userService.registerUser(user, user.getUserName()), HttpStatus.OK);
        }
        catch(FileAlreadyExistsException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    /**
     * GET mapping to get the user details
     * @param attendeeId the id of the user document
     * @return the User object or error message based on the operations
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") String attendeeId){
        try {
            return new ResponseEntity<>(userService.getUser(attendeeId), HttpStatus.OK);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



    //    TRAINING SERVICES

    /**
     * GET mapping to get all trainings of the system
     * @return list of trainings available in the system.
     */
    @GetMapping("/trainings")
    public ResponseEntity<List<Training>> getAllTrainings(){
        return new ResponseEntity<>(trainingService.allTrainings(), HttpStatus.OK);
    }
}


