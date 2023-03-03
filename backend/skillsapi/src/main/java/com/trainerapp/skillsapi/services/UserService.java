package com.trainerapp.skillsapi.services;

import com.trainerapp.skillsapi.models.User;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

/**
 * Training interface which declared all the required functionalities
 * It represents the model in the MVC architecture.
 * It represents the service layer in the layered architecture.
 * It implements low coupling pattern of the GRASP patterns as it is not dependent on other classes and has low coupling with the repository as it only declares the methods to be used.
 * It implements high cohesion of GRASP pattern as it handles only user related operations.
 */

public interface UserService {
    User getUser(String userId) throws FileNotFoundException;
    User registerUser(User user, String userName) throws FileAlreadyExistsException;
    User loginUser(String userName, String password) throws FileNotFoundException, AccessDeniedException;
    List getAllUsers();

}
