package com.trainerapp.skillsapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;
/**
 * Admin user class for users with role type as ADMIN
 * Setters and getters are added with @AllArgsConstructor and @NoArgsConstructor
 * It implements the high cohesion in GRASP patterns as it is focused only on storing admin user data.
 * It implements the low coupling in GRASP patterns as it is not dependent on other classes or services
 * It represents the Model in MVC architecture.
 * It represents the persistence layer in the layered architecture
 */

@Document(collection="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * User consists of a unique object id and custom string id called userId.
     *
     */
    @Id
    private ObjectId id;
    private String userId;
    @Field(value = "userName")
    private String userName;
    @Field(value = "email")
    private String email;
    private String mobNumber;
    private String role;
    private Date createdOn;
    private Date updatedOn;
    private String password;

    /**
     * Constructor for User class
     * @param userName the login id of the user
     * @param email the email of the user
     * @param mobNumber the mobile number of the user
     * @param password the password of the user
     */
    public User(String userName, String email, String mobNumber, String password){
        this.userName = userName;
        this.email = email;
        this.mobNumber = mobNumber;
        this.password = password;
    }

    /**
     * Constructor for User class
     * @param userName the login id of the user
     * @param password the password of the user
     */
    public User(String userName, String password){
        this.userName= userName;
        this.password = password;
    }

}
