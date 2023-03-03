package com.trainerapp.skillsapi.dao;


import com.trainerapp.skillsapi.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * User repository is used to connect with the users collection
 * It represents the data access layer/persistence layer in the layered architecture for the users.
 * It implements Low coupling of the GRASP pattern as it has low coupling with other classes and only interacts with the database
 *
 */
@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    /**
     * to find user with email attribute
     * @param email string attribute which is used to identify the document with
     * @return optional User document in case document does not exist
     */
    Optional<User> findByEmail(String email);
    /**
     * to find user with userName attribute
     * @param userName string attribute which is used to identify the document with
     * @return optional User document in case document does not exist
     */
    Optional<User> findByUserName(String userName);
    /**
     * to find user with userId attribute
     * @param userId string attribute which is used to identify the document with
     * @return User document found with the custom id
     */
    User findByUserId(String userId);
    /**
     * to delete user with userId attribute
     * @param userId string attribute which is used to identify the document with
     */
    void deleteByUserId(String userId);

}
