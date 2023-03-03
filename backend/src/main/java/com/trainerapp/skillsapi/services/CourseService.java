package com.trainerapp.skillsapi.services;

import com.trainerapp.skillsapi.models.Course;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
/**
 * Training interface which declared all the required functionalities
 *  It represents the model in the MVC architecture.
 *  It represents the service layer in the layered architecture.
 * It implements low coupling pattern of the GRASP patterns as it is not dependent on other classes and has low coupling with the repository as it only declares the methods to be used.
 * It implements high cohesion of GRASP pattern as it handles only course related operations.
 */
public interface CourseService {
    List<Course> courses();
    String addCourse(Course course, String userId) throws FileAlreadyExistsException, FileNotFoundException;
    String updateCourse(String id, Course course) throws FileNotFoundException;
    Course singleCourse(String id) throws FileNotFoundException;
}
