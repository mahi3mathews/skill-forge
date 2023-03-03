package com.trainerapp.skillsapi.services;

import com.trainerapp.skillsapi.models.AttendeeUser;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;

/**
 * Training interface which declared all the required functionalities
 * It represents the model in the MVC architecture.
 * It represents the service layer in the layered architecture.
 * It implements low coupling pattern of the GRASP patterns as it is not dependent on other classes and has low coupling with the repository as it only declares the methods to be used.
 * It implements high cohesion of GRASP pattern as it handles only attendee related operations.
 */
public interface AttendeeService {
     AttendeeUser getAttendeeById(String attendeeId) throws FileNotFoundException;
     String createdAttendee(String userId) throws FileAlreadyExistsException;
     String updateAttendee(String userId, AttendeeUser userDetails) throws FileNotFoundException;
    String deleteAttendee(String userId) throws FileNotFoundException;
}
