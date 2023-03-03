package com.trainerapp.skillsapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Attendee user class for users with role type as ATTENDEE which has its own collection "attendees"
 * Setters and getters are added with @AllArgsConstructor and @NoArgsConstructor
 * It implements the low coupling in GRASP patterns as it is not dependent on other classes or services
 * It represents the Model in MVC architecture.
 * It represents the persistence layer in the layered architecture
 */

@Document(collection = "attendees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeUser  {
    /**
     * AttendeeUser consists of objectId,
     * list of all training ids attendee has been accepted into,
     * list of application ids user has created, custom String id
     */
    @Id
    private ObjectId id;

    private List trainings;

    private List applications;

   private String userId;


}
