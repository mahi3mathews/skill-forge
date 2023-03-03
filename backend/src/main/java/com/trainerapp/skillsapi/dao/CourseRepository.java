package com.trainerapp.skillsapi.dao;

import com.trainerapp.skillsapi.models.Course;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Course repository is used to connect with the courses collection
 * It represents the data access layer/persistence layer in the layered architecture for the courses.
 * It implements Low coupling of the GRASP pattern as it has low coupling with other classes and only interacts with the database
 *
 */
@Repository
public interface CourseRepository extends MongoRepository<Course, ObjectId> {
    /**
     * to find course by courseId attribute
     * @param id custom string id in the document
     * @return Course object
     */
    Course findByCourseId(String id);
}
