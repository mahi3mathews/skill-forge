package com.trainerapp.skillsapi.services;

import com.trainerapp.skillsapi.models.Course;
import com.trainerapp.skillsapi.models.User;
import com.trainerapp.skillsapi.dao.CourseRepository;
import com.trainerapp.skillsapi.utilities.CourseUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.UUID;

/**
 * AdminCourseServiceImpl is the implementation class for the courseService inteface.
 * It represents the model in the MVC architecture.
 * It represents the service layer in the layered architecture.
 * It implements low coupling pattern of the GRASP patterns as it has low coupling with the repository as it only declares the methods to be used.
 * It implements the creator pattern of the GRASP patterns as it handle creation and updates of the course object.
 * It implements the Don’t Talk to Strangers pattern of the GRASP patterns as it ensures the implementation of CourseRepository is hidden from external entities.
 * It implements the information expert as it has access to course repository and can create, update and retrieve courses.
 */
@Service
public class AdminCourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseUtilities courseUtilities;

    @Autowired
    UserService userService;

    /**
     * To get the list of all courses from repository
     *
     * @return list of course objects
     */
    @Override
    public List<Course> courses() {
        return courseRepository.findAll();
    }

    /**
     * to retrieve single course based on the courseId
     * @param courseId unique custom string id attribute of the course
     * @return the found course object
     * @throws FileNotFoundException in case the course is not found
     */
    @Override
    public Course singleCourse(String courseId) throws FileNotFoundException{
        Course course = courseRepository.findByCourseId(courseId);
        if(course!=null){
            return course;
        }
        throw new FileNotFoundException("Course does not exist.");
    }

    /**
     * To add a course
     * @param course the course object which contains all details of the course
     * @param userId the admin id to verify if user request is from an ADMIN
     * @return string of success message
     * @throws FileAlreadyExistsException in case the course is already created
     * @throws FileNotFoundException in case the user does not exist
     */
    @Override
    public String addCourse(Course course, String userId) throws FileAlreadyExistsException, FileNotFoundException {
        User user = userService.getUser(userId);
        if(!user.getRole().equals("ADMIN")){
            throw new IllegalAccessError("User not allowed to add course.");
        }
        else if(courseUtilities.isCourseExist(courses(), course)){
            throw new FileAlreadyExistsException("Course already exists.");
        }
        course.setCourseId(UUID.randomUUID().toString());
        courseRepository.insert(course);
        return "Successfully added course.";
    }

    /**
     * to udpate the course
     * @param id unique custom string id of the course
     * @param course updated course object
     * @return string of success message
     * @throws FileNotFoundException in case the course document does not exist to be updated.
     */
    @Override
    public String updateCourse(String id, Course course) throws FileNotFoundException {
        Course existingCourse =  courseRepository.findByCourseId(id);
        if(existingCourse!=null ){
            try{
               existingCourse.setTitle(course.getTitle());
               existingCourse.setDescription(course.getDescription());
               existingCourse.setRequiredSkills(course.getRequiredSkills());
            }catch (Exception e){
                e.printStackTrace();
            }
            courseRepository.save(existingCourse);
            return "Course successfully updated.";
        }
       throw new FileNotFoundException("Course does not exist.");
    }
}
