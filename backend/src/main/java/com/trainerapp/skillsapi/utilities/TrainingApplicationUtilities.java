package com.trainerapp.skillsapi.utilities;

import com.trainerapp.skillsapi.models.Training;
import com.trainerapp.skillsapi.models.TrainingApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingApplicationUtilities {

    public boolean isTrainingIdPresent(Optional<List<TrainingApplication>> list, String id) {
        List<TrainingApplication> newList = list.get();
        for (TrainingApplication obj : newList) {
            if(obj.getTrainingId().equals(id)){
                return true;
            }
        }
        return false;
    }


    public boolean isTApplicationValid(Training training){

        Date trainingDate = training.getDate();
        long differenceMillis = new Date().getTime() - trainingDate.getTime();
        long differenceHours = differenceMillis / (60 * 60 * 1000);
        System.out.println("DIFFERENCE HOUT"+ differenceHours);
        // Check if the difference is less than 24 hours
        return differenceHours<=24;

    }
}
