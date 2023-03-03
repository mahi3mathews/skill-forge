package com.trainerapp.skillsapi.utilities;

import com.trainerapp.skillsapi.models.Training;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingUtilities {

    public List<Training> filterExpiredTrainings(List<Training> trainings){
        List<Training> filteredTrainings = new ArrayList<>();
//        Filter trainings older than 1 week
        for(Training training: trainings ){
            Instant instant = training.getDate().toInstant();
            LocalDate trainingDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate currentDate = LocalDate.now();
            System.out.println("currenDate"+ currentDate.toString());
            System.out.println("training date"+ trainingDate.toString());
            System.out.println(currentDate.isBefore(trainingDate));
            if(!currentDate.isBefore(trainingDate)){
                filteredTrainings.add(training);
            }
        }
        return filteredTrainings;
    }

    public boolean isTrainingAlreadyExist(List<Training> trainings, Training trainingToBeAdded){

        if(!trainings.isEmpty()) {
            for (Training training : trainings) {
                if (training.getDate().getTime()==trainingToBeAdded.getDate().getTime() && training.getCourseId().equals(trainingToBeAdded.getCourseId())) {
                    return true;
                }
            }
        }
        return false;
    }
}
