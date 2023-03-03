package com.trainerapp.skillsapi;

import com.trainerapp.skillsapi.models.User;
import com.trainerapp.skillsapi.dao.UserRepository;
import com.trainerapp.skillsapi.services.AttendeeService;
import com.trainerapp.skillsapi.services.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceRepoTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    AttendeeService attendeeService;

    @Mock
    private PasswordEncoder passwordEncoder;

    User testUser= new User(new ObjectId(),"USER_ID2331","User test123", "user@test.com", "12345678900", "ATTENDEE", new Date(), new Date(), "test_user");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    @Rollback(false)
    public void getAllUsersTest() throws FileNotFoundException {
        when(userRepository.findAll()).thenReturn(Stream.of(new User(new ObjectId(),"USER_ID231","User test", "user@test.com", "12345678900", "ATTENDEE", new Date(), new Date(), "test_user"), new User(new ObjectId(),"USER_ID231","User test", "user@test.com", "12345678900", "ATTENDEE", new Date(), new Date(), "test_user")).collect(Collectors.toList()));
        assertEquals(2, userService.getAllUsers().size());

    }

    @Test
    @Rollback(false)
    void testRegisterUserWhenUserAlreadyExists() {
        User user = new User();
        user.setUserName("testuser");
        when(userRepository.findByUserName("testuser")).thenReturn(Optional.of(user));

        assertThrows(FileAlreadyExistsException.class, () -> {
            userService.registerUser(user, "admin");
        });
    }

    @Test
    @Rollback(false)
    void testLoginUserWhenUserNotFound() {
        when(userRepository.findByUserName("testuser")).thenReturn(Optional.empty());
        assertThrows(FileNotFoundException.class, () -> {
            userService.loginUser("testuser", "password");
        });
    }
    @Test
    @Rollback(false)
    void testLoginUserWhenPasswordIsIncorrect() {
        User user = new User();
        user.setUserName("testuser");
        user.setPassword("password");
        when(userRepository.findByUserName("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("incorrectPassword", "password")).thenReturn(false);
        assertThrows(AccessDeniedException.class, () -> {
            userService.loginUser("testuser", "incorrectPassword");
        });
    }
    @Test
    @Rollback(false)
    void testGetUserWhenUserDoesNotExist() {
        when(userRepository.findByUserId("invalidUserId")).thenReturn(null);
        assertThrows(FileNotFoundException.class, () -> {
            userService.getUser("invalidUserId");
        });
    }

    @Test
    void contextLoads() {
    }



}

