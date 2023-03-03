package com.trainerapp.skillsapi.controllers;

import com.trainerapp.skillsapi.models.*;
import com.trainerapp.skillsapi.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Map;

/**
 * ADMIN controller is used to handle API REST calls from the admin to the system
 * It represents the presentation layer.
 * It implements the GRASP Controller pattern as it takes the request from the user in the presentation layer in the
 * application and communicates with the appropriate service layer that uses the DAO layer to update or read the database.
 * It implements the creator pattern of GRASP patterns as it creates new trainings, courses.
 * It represents the controller from the MVC architecture by managing the user input and returning the output from the database to be displayed.
 *
 */

@RestController
@RequestMapping("api/v1/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    /**
     * Admin service is used to read the permissions of the admin in case of more than one admin is created in the system
     * @Qualifier is used to mention which implementation to access.
     * TrainingService interface is used with the AdminTrainingServiceImpl implementation.
     * TrainingApplicationService interface is used with the AdminTAServiceImpl implementation.
     * CourseService interface is used with only available implementation AdminCourseServiceImpl.
     */

    @Autowired
    @Qualifier("adminTrainingService")
    private TrainingService trainingService;
    @Autowired
    @Qualifier("adminTAService")
    private TrainingApplicationService applicationService;
    @Autowired
    private CourseService courseService;



//    TRAINING SERVICES

    /**
     * This POST mapping handles the request to create a training
     * @param training is the Training object to be added into the DB
     * @return a success or error message is passed based on the operations
     */
    @PostMapping("/training/add")
    public ResponseEntity<String> createTraining(@RequestBody Training training){
        try{
            return new ResponseEntity<>(trainingService.add(training), HttpStatus.OK);
        }catch (FileNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (FileAlreadyExistsException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * This DELETE mapping is used to delete the training
     * @param trainingId is the custom id of the training used to identify the document
     * @return a string of success or error message to be displayed in UI
     */
    @DeleteMapping("/training/{trainingId}")
    public ResponseEntity<String> deleteTraining(@PathVariable String trainingId){
        return new ResponseEntity<>(trainingService.deleteTraining(trainingId), HttpStatus.NOT_FOUND);
    }

    /**
     * This PUT mapping is used to update the training with updated details
     * @param trainingId is the training id to identify the document
     * @param training is the Training object is the new incoming data
     * @return a string of success or error message to be displayed in UI
     */

    @PutMapping("/training/{trainingId}")
    public ResponseEntity<String> updateTraining(@PathVariable String trainingId, @RequestBody Training training){
        return new ResponseEntity<>(trainingService.updateTraining(trainingId, training), HttpStatus.OK);
    }


//    TRAINING APPLICATION SERVICES

    /**
     * GET mapping is used to get all applications added in the system
     * @return a string of success or error message to be displayed in UI
     */
    @GetMapping("/applications")
    public  ResponseEntity<?> getAllApplicationsForAdmin(){

        try {
            return new ResponseEntity<>( applicationService.getAllApplications(), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * This PUT mapping is used to update the application's status
     * @param applicationId the id of the document to be updated
     * @param reqPayload the status of the application to be used
     * @return a string of success or error message to be displayed in UI
     */
    @PutMapping("/application/{applicationId}/status")
    public ResponseEntity<String> updateApplicationStatus(@PathVariable String applicationId, @RequestBody Map<String, String> reqPayload){
        String status = reqPayload.get("status");
        try {
            return new ResponseEntity<>(applicationService.updateApplicationStatus(applicationId, status), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


//    COURSE SERVICES

    /**
     * GET mapping is used to get all courses added to the system
     * @return a list of all courses
     */
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses(){
        return new ResponseEntity<>(courseService.courses(), HttpStatus.OK);
    }


    /**
     * The POST mapping is used to create a new course to the system
     * @param payload contains the userId and the Course object to be added
     * @return a string of success or error message to be displayed in UI
     */

    @PostMapping("/courses/add")
    public ResponseEntity<String> addNewCourse(@RequestBody Map<String, ?> payload){
        try{
            Course course = new Course((Map)payload.get("course"));
            String userId = (String) payload.get("userId");

         return new ResponseEntity<>(courseService.addCourse(course, userId), HttpStatus.OK);
        }catch (FileAlreadyExistsException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * PUT mapping is used to update the course
     * @param courseId id of the document to be updated
     * @param course incoming Course object to update with
     * @return a string of success or error message to be displayed in UI
     */
    @PutMapping("/courses/{courseId}")
    public ResponseEntity<String> updateExistingCourse(@PathVariable("courseId") String courseId, @RequestBody Course course){
        try{
           return new ResponseEntity<>( courseService.updateCourse(courseId, course), HttpStatus.OK);
        }catch (FileNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



}
