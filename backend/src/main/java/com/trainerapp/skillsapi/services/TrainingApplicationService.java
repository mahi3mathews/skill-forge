package com.trainerapp.skillsapi.services;
import com.trainerapp.skillsapi.models.TrainingApplication;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
/**
 * Training interface which declared all the required functionalities
 *  It represents the model in the MVC architecture.
 *  It represents the service layer in the layered architecture.
 * It implements low coupling pattern of the GRASP patterns as it is not dependent on other classes and has low coupling with the repository as it only declares the methods to be used.
 * It implements high cohesion of GRASP pattern as it handles only training-application related operations.
 */
public interface TrainingApplicationService {
 List<Map> getAllApplications();
 TrainingApplication singleApplication(String id);
 List<Map> getAttendeeApplications(String id);
 void updateApplication(TrainingApplication application);
 String deleteApplication(String id) throws FileNotFoundException;
 String createApplication(TrainingApplication application) throws Exception;
 String updateApplicationStatus(String appId, String status) throws FileNotFoundException ;
}
