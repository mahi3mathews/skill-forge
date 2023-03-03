package com.trainerapp.skillsapi.dao;

import com.trainerapp.skillsapi.models.Training;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Training repository is used to connect with the trainings collection
 * It represents the data access layer/persistence layer in the layered architecture for the trainings.
 * It implements Low coupling of the GRASP pattern as it has low coupling with other classes and only interacts with the database
 *
 */
@Repository
public interface TrainingRepository extends MongoRepository<Training, ObjectId> {

    /**
     * to find course with course id attribute
     * @param courseId custom string id attribute of course present in the training document
     * @return the training found with the id
     */
    public Training findByCourseId(String courseId);

    /**
     * to delete the training with custom training id attribute
     * @param trainingId custom string id of the document
     */
    public void deleteByTrainingId(String trainingId);

    /**
     * to find training document with custom string trainingId attribute
     * @param trainingId custom string id of the document
     * @return training object found with the id
     */
    public Training findByTrainingId(String trainingId);
}
