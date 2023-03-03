package com.trainerapp.skillsapi;

import com.trainerapp.skillsapi.models.Course;
import com.trainerapp.skillsapi.models.User;
import com.trainerapp.skillsapi.dao.CourseRepository;
import com.trainerapp.skillsapi.services.AdminCourseServiceImpl;
import com.trainerapp.skillsapi.services.UserService;
import com.trainerapp.skillsapi.utilities.CourseUtilities;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CourseServiceRepoTest {

    @Autowired
    private AdminCourseServiceImpl adminCourseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseUtilities courseUtilities;

    @Mock
    private UserService userService;

    @Test
    public void testAddCourse() throws FileAlreadyExistsException, FileNotFoundException {
        // Arrange
        Course course = new Course();
        course.setCourseId(UUID.randomUUID().toString());
        course.setTitle("Test Course");
        course.setDescription("Test Course Description");
        List<String> requiredSkills = new ArrayList<>();
        requiredSkills.add("Test Skill 1");
        requiredSkills.add("Test Skill 2");
        course.setRequiredSkills(requiredSkills);
        User adminUser = new User();
        adminUser.setRole("ADMIN");
        String userId = UUID.randomUUID().toString();
        when(userService.getUser(userId)).thenReturn(adminUser);
        when(courseUtilities.isCourseExist(any(), any())).thenReturn(false);
        when(courseRepository.insert(course)).thenReturn(course);

        // Act
        String result = adminCourseService.addCourse(course, userId);

        // Assert
        assertThat(result).isEqualTo("Successfully added course.");
    }

    @Test
    public void testAddCourseWithExistingCourse() throws FileAlreadyExistsException, FileNotFoundException {
        // Arrange
        Course course = new Course();
        course.setCourseId(UUID.randomUUID().toString());
        course.setTitle("Test Course");
        course.setDescription("Test Course Description");
        List<String> requiredSkills = new ArrayList<>();
        requiredSkills.add("Test Skill 1");
        requiredSkills.add("Test Skill 2");
        course.setRequiredSkills(requiredSkills);
        User adminUser = new User();
        adminUser.setRole("ADMIN");
        String userId = UUID.randomUUID().toString();
        when(userService.getUser(userId)).thenReturn(adminUser);
        when(courseUtilities.isCourseExist(any(), any())).thenReturn(true);

        // Act and Assert
        assertThrows(FileAlreadyExistsException.class, () -> adminCourseService.addCourse(course, userId));
    }

}

        // Act and Assert
