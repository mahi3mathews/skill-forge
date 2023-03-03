package com.trainerapp.skillsapi.services;

import com.trainerapp.skillsapi.models.AttendeeUser;
import com.trainerapp.skillsapi.models.Training;
import com.trainerapp.skillsapi.models.TrainingApplication;
import com.trainerapp.skillsapi.dao.TrainingApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AdminTAServiceImpl is the implementation class for the trainingApplication inteface.
 * It represents the model in the MVC architecture.
 * It represents the service layer in the layered architecture.
 * It implements the Don’t Talk to Strangers pattern of the GRASP patterns as it ensures the implementation of TrainingApplicationRepository is hidden from external entities.
 */
@Service("adminTAService")
public class AdminTAServiceImpl implements TrainingApplicationService {
    @Autowired
    private TrainingApplicationRepository applicationRepository;

    @Autowired
    @Qualifier("adminTrainingService")
    private TrainingService trainingService;

    @Autowired
    private AttendeeService attendeeService;


    /**
     * to get all applications with the training title, training date, list of participants, total seats available
     * @return list of all applications with the required attributes from training
     */
    @Override
    public List<Map> getAllApplications() {

        List<TrainingApplication> applications =applicationRepository.findAll();
        List<Map> applicationList = new ArrayList<>();
        if(applications!=null) {
            for(TrainingApplication application:applications){
                Map<String, Object> outputRes = new HashMap<>();
                Training training = trainingService.singleTraining(application.getTrainingId());
                outputRes.put("status", application.getStatus());
                outputRes.put("applicationId", application.getApplicationId());
                outputRes.put("statement", application.getStatement());
                outputRes.put("trainingTitle", training.getTitle());
                outputRes.put("trainingId", application.getTrainingId());
                outputRes.put("trainingDate", training.getDate());
                outputRes.put("totalSeats", training.getTotalSeats());
                outputRes.put("participants", training.getParticipants());

                applicationList.add(outputRes);
            }

        }
        return applicationList;
    }

    /**
     * to get a single application
     * @param id custom id of the application
     * @return an object of TrainingsApplication
     */
    @Override
    public TrainingApplication singleApplication(String id){
        return applicationRepository.findByApplicationId(id);
    }

    /**
     * to get applications with attendeeId. Not used in admin services
     * @param id
     * @return null
     */
    @Override
    public List<Map> getAttendeeApplications(String id){
        return null;
    }

    /**
     * to update the application
     * @param application new application details to be updated to the repository
     */
    @Override
    public void updateApplication(TrainingApplication application){
        applicationRepository.save(application);
    }

    /**
     * Not implemented for admin
     * @param applicationId
     * @return string of success message
     * @throws FileNotFoundException in case application does not exist
     */
    @Override
    public String deleteApplication(String applicationId) throws FileNotFoundException {
        return null;
    }

    /**
     * Not implemented for admin
     * @param application
     * @return
     */
    @Override
    public String createApplication(TrainingApplication application) {
        throw new UnsupportedOperationException("Admin cannot create application.");
    }

    /**
     * to update the status of the application
     * @param id custom id of the application document
     * @param status string value to be updated in the application document
     * @return string of success message
     * @throws FileNotFoundException in case the application is not found
     */
    @Override
    public String updateApplicationStatus(String id, String status) throws FileNotFoundException {
        TrainingApplication application = singleApplication(id) ;

        if(application!=null){
            application.setStatus(status);

            if(status.toLowerCase().equals("accepted")){
// To-do:                 Add implementation layer between admin and attendee services
                Training training = trainingService.singleTraining(application.getTrainingId());
//            Update the attendee with the training id
                AttendeeUser newAttendee = attendeeService.getAttendeeById(application.getAttendeeId());
                List trainings= newAttendee.getTrainings();
                trainings.add(training.getTrainingId());
                newAttendee.setTrainings(trainings);
                System.out.println("Applications size"+ newAttendee.getApplications().size());
                try {
                    attendeeService.updateAttendee(newAttendee.getUserId(), newAttendee);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
//            Update the training with attendee id
                training.updateParticipants(newAttendee.getUserId());
                try {
                    trainingService.updateTrainingApplicants(training.getTrainingId(),training);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                updateApplication(application);
                return  "Application has been accepted";
            }
            else if(status.toLowerCase().equals("rejected")) {
                System.out.println(status+"status sent"+ application.getStatus());
                updateApplication(application);
//                To-do: if attendee should not be allowed to apply again then add a list of rejectedTrainings.
                return "Application has been rejected";
            }
            else {
                throw new IllegalArgumentException("Incorrect status provided.")   ;
            }
        }
        return "Application not found.";
    }
}
