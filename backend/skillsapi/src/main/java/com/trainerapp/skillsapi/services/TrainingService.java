package com.trainerapp.skillsapi.services;

import com.trainerapp.skillsapi.models.Training;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

/**
 * Training interface which declared all the required functionalities
 * It represents the model in the MVC architecture.
 * It represents the service layer in the layered architecture.
 * It implements low coupling pattern of the GRASP patterns as it is not dependent on other classes and has low coupling with the repository as it only declares the methods to be used.
 * It implements high cohesion of GRASP pattern as it handles only training related operations.
 * It implements polymorphism of GRASP patterns as it has two implementations AdminTrainingServiceImpl and AttendeeTrainingServiceImpl
 */
public interface TrainingService {
    List<Training> allTrainings();

    String add(Training training) throws FileNotFoundException, FileAlreadyExistsException;

    public Training singleTraining(String id);

    String updateTrainingApplicants(String trainingId, Training training) throws IllegalAccessException;

    String updateTraining(String trainingId, Training training);

    String deleteTraining(String trainingId);

    Training getTrainingByCourseId(String courseId);
    List<Training> getAttendeeTrainings(String userId) throws FileNotFoundException;
}
