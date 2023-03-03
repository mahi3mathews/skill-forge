package com.trainerapp.skillsapi.services;

import com.trainerapp.skillsapi.models.AttendeeUser;
import com.trainerapp.skillsapi.models.Training;
import com.trainerapp.skillsapi.models.TrainingApplication;
import com.trainerapp.skillsapi.dao.TrainingApplicationRepository;
import com.trainerapp.skillsapi.utilities.TrainingApplicationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;


/**
 * AdminCourseServiceImpl is the implementation class for the courseService interface.
 * It represents the model in the MVC architecture.
 * It represents the service layer in the layered architecture.
 * It implements the creator pattern of the GRASP patterns as it handle creation of the training application object.
 * It implements the Don’t Talk to Strangers pattern of the GRASP patterns as it ensures the implementation of TrainingApplicationRepository is hidden from external entities.
 * It implements the information expert as it has access to training-application repository and can create, delete and retrieve training-applications.
 */
@Service("attendeeTAService")
public class AttendeeTAServiceImpl implements TrainingApplicationService {
    @Autowired
    TrainingApplicationRepository applicationRepository;

    @Autowired
    TrainingApplicationUtilities applicationUtilities;
    @Autowired
    @Qualifier("attendeeTrainingService")
    TrainingService trainingService;
    @Autowired
    AttendeeService attendeeService;


    @Override
    public List<Map> getAllApplications() {
        return null;
    }

    @Override
    public TrainingApplication singleApplication(String id) {
        return applicationRepository.findByApplicationId(id);
    }

    @Override
    public List<Map> getAttendeeApplications(String id) {
        Optional<List<TrainingApplication>> optionalApplications= applicationRepository.findByAttendeeId(id);
        List<Map> applicationList = new ArrayList<>();
        if(optionalApplications.isPresent()) {
            List<TrainingApplication> applications = optionalApplications.get();
            for(TrainingApplication application:applications){
                Map<String, Object> outputRes = new HashMap<>();
                Training training = trainingService.singleTraining(application.getTrainingId());
                outputRes.put("status", application.getStatus());
                outputRes.put("applicationId", application.getApplicationId());
                outputRes.put("statement", application.getStatement());
                outputRes.put("trainingTitle", training.getTitle());
                outputRes.put("trainingId", application.getTrainingId());
                outputRes.put("trainingDate", training.getDate());
                applicationList.add(outputRes);
            }

        }
        return applicationList;

    }

    @Override
    public void updateApplication(TrainingApplication application) {
        throw new UnsupportedOperationException("User does not have permission.");
    }

    @Override
    public String deleteApplication(String id) throws FileNotFoundException {
        if(singleApplication(id)!=null){
            TrainingApplication application = applicationRepository.findByApplicationId(id);
//            To-do: if the status is accepted then remove from training as well
            AttendeeUser user = attendeeService.getAttendeeById(application.getAttendeeId());
            List applications = user.getApplications();
            applications.remove(id);
            user.setApplications(applications);
            attendeeService.updateAttendee(user.getUserId(), user);
            applicationRepository.deleteByApplicationId(id);
            return "Application successfully removed.";
        }
        else {
            throw new FileNotFoundException("Application not found");
        }

    }
    @Override
    public String createApplication(TrainingApplication application) throws Exception {
        System.out.println(application.getAttendeeId());
        Optional<List<TrainingApplication>> existingApplication = applicationRepository.findByAttendeeId(application.getAttendeeId());
        AttendeeUser attendeeUser = attendeeService.getAttendeeById(application.getAttendeeId());
        if(existingApplication.isPresent() && applicationUtilities.isTrainingIdPresent(existingApplication,application.getTrainingId() ) ){
            throw new FileAlreadyExistsException("Application already exists.");
        }
        else if(existingApplication.isPresent() && applicationUtilities.isTApplicationValid(trainingService.singleTraining(application.getTrainingId()))) {
            application.setApplicationId(UUID.randomUUID().toString());
            application.setStatus("PENDING");
            List attendeeApplications = attendeeUser.getApplications();
            attendeeApplications.add(application.getApplicationId());
            attendeeUser.setApplications(attendeeApplications);
            attendeeService.updateAttendee(attendeeUser.getUserId(), attendeeUser);
            applicationRepository.insert(application);
            return "Application successfully created.";
        }
        throw new Exception("Applications are closed for this training.");
    }

    @Override
    public String updateApplicationStatus(String appId, String status) {
        throw new UnsupportedOperationException("User is not allowed to update status.");
    }
}
