package com.trainerapp.skillsapi.dao;

import com.trainerapp.skillsapi.models.TrainingApplication;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * TrainingApplication repository is used to connect with the trainingApplications collection
 * It represents the data access layer/persistence layer in the layered architecture for the trainingApplications.
 * It implements Low coupling of the GRASP pattern as it has low coupling with other classes and only interacts with the database
 *
 */
@Repository
public interface TrainingApplicationRepository extends MongoRepository<TrainingApplication, ObjectId> {
     /**
      * to find applications with attendeeId
      * @param id userId of the attendee present
      * @return optional list of trainingApplication objects if no documents are found with the id
      */
     Optional<List<TrainingApplication>> findByAttendeeId(String id);

     /**
      * to find with custom application id attribute
      * @param id custom string id of an application
      * @return TrainingApplication object found with the id
      */
     TrainingApplication findByApplicationId(String id);

     /**
      * to delete application with custom string id
      * @param applicationId custom application id attribute
      */
     void deleteByApplicationId(String applicationId);
}
