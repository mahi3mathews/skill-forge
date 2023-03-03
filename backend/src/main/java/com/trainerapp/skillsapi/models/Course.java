package com.trainerapp.skillsapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Course class to be linked with training which has its own collection "attendees"
 * Setters and getters are added with @AllArgsConstructor and @NoArgsConstructor
 * It implements the high cohesion in GRASP patterns as it is focused only on storing course data.
 * It implements the low coupling in GRASP patterns as it is not dependent on other classes or services
 * It represents the Model in MVC architecture.
 * It represents the persistence layer in the layered architecture
 */

@Document(collection="courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    /**
     * Course consists of a unique object id and custom string id called courseId.
     * It consists of attributes like title, description and list of requiredSkills
     */
    @Id
    private ObjectId id;
    private String courseId;
    private String title;
    private String description;
    private List<String> requiredSkills;

    /**
     * Constructor for Course class
     * @param course is the Map object which consist of title, description and requiredSkills
     */
    public Course(Map course){
        this.title = (String) course.get("title");
        this.description=(String)course.get("description");
        this.requiredSkills=(ArrayList<String>)course.get("requiredSkills");
    }


}
