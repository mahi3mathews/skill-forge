package com.trainerapp.skillsapi.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Admin user class for users with role type as ADMIN
 * Setters and getters are added with @AllArgsConstructor and @NoArgsConstructor
 * It represents the Model in MVC architecture.
 * It represents the persistence layer in the layered architecture
 * It implements the high cohesion in GRASP patterns as it is focused only on storing admin user data.
 * It implements the low coupling in GRASP patterns as it is not dependent on other classes or services
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUser extends User {
    /**
     * permissions are the list of string attribute of the admin class
     */
    private List<String> permissions;

}
