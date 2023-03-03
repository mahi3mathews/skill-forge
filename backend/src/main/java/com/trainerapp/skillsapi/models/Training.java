package com.trainerapp.skillsapi.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Date;
import java.util.List;

/**
 * Training class has its own collection "trainings"
 * Setters and getters are added with @AllArgsConstructor and @NoArgsConstructor
 * It implements the low coupling in GRASP patterns as it is not dependent on other classes or services
 * It represents the Model in MVC architecture.
 * It represents the persistence layer in the layered architecture
 */

@Document(collection = "trainings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    /**
     * Training consists of a unique object id and custom string id called trainingId.
     * It has courseId which helps in linking the training to the course.
     * It has list of participant ids which is used to link the training to the different attendees.
     */
    @Id
    private ObjectId id;
    private String trainingId;
    private Date date;
    private int totalSeats;
    private  Date createdAt;
    private Date updatedAt;
    private String title;
    private String description;
    private List participants;
    private String courseId;
    private String imgUrl;

    /**
     * Constructor for Training class
     * @param title is the title for the training
     * @param description is a string of information about the training
     * @param totalSeats is the number of seats available for the training
     * @param imgUrl is the image URL to displayed to the user in the presentation layer
     * @param courseId is the custom unique courseId to link training to the course
     * @param date is the date for the training
     */
    public Training(String title, String description,int totalSeats, String imgUrl,String courseId, Date date){
        this.title=title;
        this.description = description;
        this.totalSeats= totalSeats;
        this.imgUrl = imgUrl;
        this.courseId = courseId;
        this.date = date;

    }

    /**
     * to add an id to the list of participants
     * @param id is the id of the participants present in the training
     */
    public void updateParticipants(String id){
        this.participants.add(id);
    }

}
