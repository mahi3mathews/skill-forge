package com.trainerapp.skillsapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * TrainingApplication class has its own collection "training-application"
 * Setters and getters are added with @AllArgsConstructor and @NoArgsConstructor
 * It implements the low coupling in GRASP patterns as it is not dependent on other classes or services
 * It represents the Model in MVC architecture.
 * It represents the persistence layer in the layered architecture
 */
@Document(collection = "training-applications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingApplication {
    /**
     * TrainingApplication consists of a unique object id and custom string id called applicationId.
     * It has trainingId which helps in linking the application to the training.
     * It has attendeeId which helps in linking the application to the attendee.
     */
    @Id
    private ObjectId id;
    private String applicationId;
    private String trainingId;
    private String status;
    private String statement;

    private String attendeeId;

    /**
     * constructor for TrainingApplication
     * @param attendeeId the unique custom id of the attendee linked to the application
     * @param trainingId the unique custom id of the training linked to the application
     * @param statement the statement provided by the attendee for the application
     */
    public TrainingApplication (String attendeeId, String trainingId, String statement){
        this.trainingId=trainingId;
        this.attendeeId=attendeeId;
        this.statement = statement;
    }

    /**
     * constructor for TrainingApplication
     * @param newApplication a TrainingApplication object is provided to create with trainingId, status and attendeeId
     */
    public TrainingApplication(TrainingApplication newApplication) {
        this.trainingId = newApplication.getTrainingId();
        this.status = newApplication.getStatus();
        this.attendeeId = newApplication.getAttendeeId();

    }
}
