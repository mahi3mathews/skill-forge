package com.trainerapp.skillsapi.dao;

import com.trainerapp.skillsapi.models.AttendeeUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Attendee repository interface is used to connect with the attendee collection using MongoRepository.
 * It represents the data access layer/persistence layer in the layered architecture for the attendee.
 * It implements Low coupling of the GRASP pattern as it has low coupling with other classes and only interacts with the database
 *
 */
public interface AttendeeRepository extends MongoRepository<AttendeeUser, ObjectId> {
    /**
     * To find by userId
     * @param userId id of user in the document
     * @return an optional AttendeeUser object in case document is not found
     */
    Optional<AttendeeUser> findByUserId(String userId);

    /**
     * To delete by userId
     * @param userId id of user in the document
     * @return null
     */
    void deleteByUserId(String userId);
}
