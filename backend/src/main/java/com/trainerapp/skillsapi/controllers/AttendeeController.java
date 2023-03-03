package com.trainerapp.skillsapi.controllers;


import com.trainerapp.skillsapi.services.*;
import com.trainerapp.skillsapi.models.TrainingApplication;
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
 * ATTENDEE controller is used to handle API REST calls from the attendee to the system
 * It represents the user interface layer.
 * It implements the GRASP Controller pattern as it takes the request from the user in the presentation layer in the application and communicates with the appropriate service layer that uses the DAO layer to update or read the database
 * It represents the controller from the MVC architecture by managing the user input and returning the output from the database to be displayed.
 * It implements the creator pattern of GRASP patterns as it creates new training-applications.
 */

@RestController
@RequestMapping("api/v1/attendee")
@CrossOrigin(origins = "http://localhost:3000")
public class AttendeeController {

    /**
     * @Qualifier is used to mention which implementation to access.
     * TrainingService interface is used with the AttendeeTrainingServiceImpl implementation.
     * TrainingApplicationService interface is used with the AttendeeTAServiceImpl implementation.
     */

    @Autowired
    @Qualifier("attendeeTAService")
    TrainingApplicationService applicationService;

    @Autowired @Qualifier("attendeeTrainingService")
    TrainingService trainingService;



//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateUserDetails(@PathVariable("id") String attendeeId, @RequestBody AttendeeUser userDetails){
//        try{
//            return new ResponseEntity<>( attendeeService.updateAttendee(attendeeId,userDetails), HttpStatus.OK);
//        }catch(FileNotFoundException e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }catch(InternalError e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteAttendee(@PathVariable("id") String userId){
//        try {
//            return new ResponseEntity<String>( attendeeService.deleteAttendee(userId), HttpStatus.OK);
//        }catch (FileNotFoundException e){
//            return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
//        }
//    }

//    TRAINING APPLICATION


    /**
     * GET mapping to get all applications of the user based on the userId
     * @param userId is the id of the attendee based on which the applications are retrieved
     * @return a list of applications
     */
    @GetMapping("/{userId}/applications")
    public ResponseEntity<List<Map>> getUserApplications(@PathVariable("userId") String userId){
        return new ResponseEntity<>(applicationService.getAttendeeApplications(userId), HttpStatus.OK);
    }

    /**
     * POST mapping to create a training application
     * @param trainingApplication is the Training object which needs to be added
     * @return a string to show success or error message in the UI
     */
    @PostMapping("/application/create")
    public ResponseEntity<String> createApplication(@RequestBody TrainingApplication trainingApplication){
       try{
           return new ResponseEntity<>(applicationService.createApplication(trainingApplication), HttpStatus.OK);
       }catch (FileAlreadyExistsException e){
           return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       }catch (Exception e){
           return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
       }

    }

    /**
     * DELETE mapping is used to delete the application from the system
     * @param applicationId the id of the application to be deleted
     * @return a string to show success or error message in the UI
     */
    @DeleteMapping("/application/{applicationId}")
    public ResponseEntity<String> deleteApplication(@PathVariable("applicationId") String applicationId){
        try {
            return new ResponseEntity<>(applicationService.deleteApplication(applicationId), HttpStatus.OK);
        }catch (FileNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

//    TRAINING SERVICES

    /**
     * GET mapping is used to get all trainings where the attendee has been accepted
     * @param userId the id of the attendee
     * @return list of trainings or a string to show error message in the UI
     */
    @GetMapping("/trainings")
    public ResponseEntity<?> getAllUserTrainings(@RequestBody String userId){
        try {
            return new ResponseEntity<>(trainingService.getAttendeeTrainings(userId), HttpStatus.OK);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

}
