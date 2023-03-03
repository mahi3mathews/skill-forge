package com.trainerapp.skillsapi.services;

import com.trainerapp.skillsapi.models.AttendeeUser;
import com.trainerapp.skillsapi.models.Training;
import com.trainerapp.skillsapi.dao.TrainingRepository;
import com.trainerapp.skillsapi.utilities.TrainingUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * AttendeeTrainingServiceImpl is the implementation class for the TrainingService interface.
 * It represents the model in the MVC architecture.
 * It represents the service layer in the layered architecture.
 * It implements the Don’t Talk to Strangers pattern of the GRASP patterns as it ensures the implementation of TrainingRepository is hidden from external entities.
 */
@Service("attendeeTrainingService")
public class AttendeeTrainingServiceImpl implements TrainingService {
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private AttendeeService attendeeService;
    @Autowired
    TrainingUtilities trainingUtilities;
    @Override
    public List<Training> allTrainings() {
        List<Training> trainings =  trainingRepository.findAll();
        return trainingUtilities.filterExpiredTrainings(trainings);
    }

    @Override
    public String add(Training training)  {
        throw new UnsupportedOperationException("User does not have permission.");
    }

    @Override
    public Training singleTraining(String id) {
        return trainingRepository.findByTrainingId(id);
    }

    @Override
    public String updateTrainingApplicants(String trainingId, Training training) throws IllegalAccessException {
        throw new IllegalAccessException("User does not have permission.");
    }

    @Override
    public String updateTraining(String trainingId, Training training) {
        throw new UnsupportedOperationException("User does not have permission.");
    }

    @Override
    public String deleteTraining(String trainingId) {
        throw new UnsupportedOperationException("User does not have permission.");
    }

    @Override
    public Training getTrainingByCourseId(String courseId) {
        return trainingRepository.findByCourseId(courseId);
    }

    @Override
    public List<Training> getAttendeeTrainings(String userId) throws FileNotFoundException {
        AttendeeUser attendee = attendeeService.getAttendeeById(userId);
        List<String> trainingIds = attendee.getTrainings();
        List<Training> trainings = new ArrayList<>();
        for(String id : trainingIds){
            trainings.add(singleTraining(id));
        }
        return trainings;
    }
}
