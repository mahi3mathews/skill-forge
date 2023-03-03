package com.trainerapp.skillsapi.services;

import com.trainerapp.skillsapi.models.Training;
import com.trainerapp.skillsapi.dao.TrainingRepository;
import com.trainerapp.skillsapi.utilities.TrainingUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * AdminTrainingServiceImpl is the implementation class for the trainingService inteface.
 * It represents the model in the MVC architecture.
 * It represents the service layer in the layered architecture.
 * It implements the creator pattern of the GRASP patterns as it handle creation and updates of the training object.
 * It implements the Don’t Talk to Strangers pattern of the GRASP patterns as it ensures the implementation
 * of TrainingRepository functionalities are hidden from external entities.
 * It implements the information expert as it has access to training repository and can create, update and retrieve training.
 * It implements the indirection pattern of GRASP patterns as it implements the Training Service interface and
 * provides an abstraction layer between the controller and the data access layer.
 */
@Service("adminTrainingService")
public class AdminTrainingServiceImpl implements TrainingService {
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private TrainingUtilities trainingUtilities;

    /**
     * to get all trainings for the admin
     * @return a list of training objects
     */
    @Override
    public List<Training> allTrainings() {
        return trainingRepository.findAll();
    }

    /**
     * to add a training to the repository
     * @param training the object to be added to training repository
     * @return string of success message
     * @throws FileAlreadyExistsException in case the training already exists
     */
    @Override
    public String add(Training training) throws FileAlreadyExistsException {
        String result;

        if(trainingUtilities.isTrainingAlreadyExist(allTrainings(), training)){
            throw new FileAlreadyExistsException("Training already exists for this course at specified time.");
        }
        else {
            training.setTrainingId(UUID.randomUUID().toString());
            training.setCreatedAt(new Date());
            training.setUpdatedAt(new Date());
            training.setParticipants(new ArrayList<>());
            trainingRepository.insert(training);

            result = "Training successfully added for this course.";
        }
        return result;
    }

    /**
     * to retrieve a single training document
     * @param id unique custom string id of training document
     * @return the found training object
     */
    @Override
    public Training singleTraining(String id) {
        return trainingRepository.findByTrainingId(id);
    }

    /**
     * to update the applicants in the training object
     * @param trainingId the custom id of the training document
     * @param training the object with new applicants
     * @return string success message
     */
    @Override
    public String updateTrainingApplicants(String trainingId, Training training) {
        Training existingTraining = singleTraining(trainingId);
        if(existingTraining!=null){
            existingTraining.setParticipants(training.getParticipants());
            try{
                trainingRepository.save(existingTraining);}
            catch (Exception e){
                e.printStackTrace();
            }
            return "Training was successfully updated.";
        }
        else return "Training not found.";

    }

    /**
     * to update the training document
     * @param trainingId custom id of the document used to retrieve the object
     * @param training updated object to be applied ot existing document
     * @return string message of success
     */
    @Override
    public String updateTraining(String trainingId, Training training) {
        Training existingTraining = singleTraining(trainingId);
        if(existingTraining!=null){

            existingTraining.setDate(training.getDate());
            existingTraining.setDescription(training.getDescription());
            existingTraining.setTitle(training.getTitle());
            existingTraining.setCourseId(training.getCourseId());
            existingTraining.setImgUrl(training.getImgUrl());
            existingTraining.setTotalSeats(training.getTotalSeats());
            existingTraining.setUpdatedAt(new Date());
            existingTraining.setParticipants(existingTraining.getParticipants());
           try{
            trainingRepository.save(existingTraining);}
            catch (Exception e){
                e.printStackTrace();
            }
            return "Training was successfully updated.";
        }
        else return "Training not found.";

    }

    /**
     * to delete the training from the repository
     * @param trainingId the string
     * @return
     */
    @Override
    public String deleteTraining(String trainingId) {
        Training training = singleTraining(trainingId);
        if(training!=null){
            trainingRepository.deleteByTrainingId(trainingId);
            return "Training successfully removed.";
        }
        else return "Training does not exist";
    }

    @Override
    public Training getTrainingByCourseId(String courseId) {
        return trainingRepository.findByCourseId(courseId);
    }

    @Override
    public List<Training> getAttendeeTrainings(String userId) throws FileNotFoundException {
       return null;
    }
}
