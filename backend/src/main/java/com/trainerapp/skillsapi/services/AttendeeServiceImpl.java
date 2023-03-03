package com.trainerapp.skillsapi.services;

import com.trainerapp.skillsapi.models.AttendeeUser;
import com.trainerapp.skillsapi.dao.AttendeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * AttendeeServiceImpl is the implementation class for the AttendeeService interface.
 * It represents the model in the MVC architecture.
 * It represents the service layer in the layered architecture.
 * It implements the creator pattern of the GRASP patterns as it handle creation and updates of the course object.
 * It implements the Don’t Talk to Strangers pattern of the GRASP patterns as it ensures the implementation of AttendeeRepository is hidden from external entities.
 * It implements the information expert of GRASP patterns as it has access to attendee repository and can create, update and retrieve attendees.
 */
@Service
public class AttendeeServiceImpl implements AttendeeService {

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Override
    public AttendeeUser getAttendeeById(String attendeeId) throws FileNotFoundException {
        System.out.println("attende id" + attendeeId+ " " );
        Optional<AttendeeUser> user = attendeeRepository.findByUserId(attendeeId);
        if(user.isEmpty()){
            throw new FileNotFoundException("User does not exist");
        }
        return user.get();
    }
    @Override
    public String createdAttendee(String userId) throws FileAlreadyExistsException {
        Optional<AttendeeUser> user1 = attendeeRepository.findByUserId(userId);
        System.out.println("Attendee user1: "+ user1.isPresent());
        System.out.println("Attendee user1: "+ userId);
        if(user1.isPresent()){
            throw new FileAlreadyExistsException("Attendee already exists.");
        }
        else {
            AttendeeUser attendee = new AttendeeUser();
            attendee.setUserId(userId);
            attendee.setApplications(new ArrayList<>());
            attendee.setTrainings(new ArrayList<>());
            attendeeRepository.insert(attendee);
            return "Attendee was successfully created.";
        }
    }
    @Override
    public String updateAttendee(String userId, AttendeeUser userDetails) throws FileNotFoundException {
        Optional<AttendeeUser> user = attendeeRepository.findByUserId(userId);
        System.out.println("ATTENDEE SERVICE");
        System.out.println(user.isPresent());
        System.out.println(user.isEmpty());

        if(user.isPresent() ){
            try{
                AttendeeUser attendee = user.get();
               attendee.setTrainings(userDetails.getTrainings());
               attendee.setApplications(userDetails.getApplications());
               System.out.println(attendee.getApplications().size());
               System.out.println(attendee.getUserId());
                System.out.println(attendee.getTrainings().size());

               attendeeRepository.save(userDetails);
               return "Attendee has been accepted into the training";
            }catch (Exception e){
                e.printStackTrace();
                throw new InternalError("User could not be updated.");
            }
        }
        else {
            throw new FileNotFoundException("User does not exist.");
        }

    }
    @Override
    public String deleteAttendee(String userId) throws FileNotFoundException{
        Optional<AttendeeUser> user1 =  attendeeRepository.findByUserId(userId);
        if(user1.isPresent()){
            attendeeRepository.deleteByUserId(userId);
            return "Attendee successfully removed.";
        }else{
            throw new FileNotFoundException("Attendee does not exist.");
        }
    }

}
