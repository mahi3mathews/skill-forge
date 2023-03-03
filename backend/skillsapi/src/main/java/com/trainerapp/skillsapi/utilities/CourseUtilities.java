package com.trainerapp.skillsapi.utilities;

import com.trainerapp.skillsapi.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class CourseUtilities {
    @Autowired
    private Course course;

    @Autowired
    public CourseUtilities(Course course){
        this.course = course;
    }
    public boolean isCourseExist(List<Course> courses, Course course){
        for(Course course1: courses){
           if(course1.getDescription().equals(course.getDescription()) && course1.getTitle().equals(course.getTitle())){
                return true;
            }
        }
        return false;
    }
}
