package com.example.SoftwareEngineeringProject.Service;


import com.example.SoftwareEngineeringProject.Entity.Role;
import com.example.SoftwareEngineeringProject.Entity.Tutor;
import com.example.SoftwareEngineeringProject.Entity.User;
import com.example.SoftwareEngineeringProject.Repository.TutorRepository;
import com.example.SoftwareEngineeringProject.exception.IdNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TutorService {

    private final  TutorRepository tutorRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public TutorService(TutorRepository tutorRepository, BCryptPasswordEncoder passwordEncoder) {
        this.tutorRepository = tutorRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<Tutor> getAllTutor(){
        return tutorRepository.findAll();
    }


    public Tutor findById(int tutorId) throws IdNotFoundException {
        return tutorRepository.findById(tutorId).orElseThrow( () -> new IdNotFoundException("Id Not Found Tutor : "+tutorId));
    }


    public Tutor createTutor(Tutor tutor){
            User user = User.builder()
                    .username(tutor.getUser().getUsername())
                    .password(passwordEncoder.encode(tutor.getUser().getPassword()))
                    .authorities(Collections.singleton(Role.ROLE_TUTOR))
                    .isCredentialsNonExpired(true)
                    .isEnabled(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .build();

            Tutor newTutor = Tutor.builder()
                    .firstName(tutor.getFirstName())
                    .lastName(tutor.getLastName())
                    .subject(tutor.getSubject())
                    .telephoneNumber(tutor.getTelephoneNumber())
                    .email(tutor.getEmail())
                    .image(tutor.getImage())
                    .description(tutor.getDescription())
                    .RegisterDate(tutor.getRegisterDate())
                    .gender(tutor.getGender())
                    .user(user)
                    .build();

            return tutorRepository.save(newTutor);


    }

    public Tutor updateTutor(int tutorId,Tutor tutor) throws IdNotFoundException{
        Optional<Tutor> tempTutor = tutorRepository.findById(tutorId);
        if(tempTutor.isPresent()){
                Tutor savedTutor = tempTutor.get();
                savedTutor.setImage(tutor.getImage());
                savedTutor.setEmail(tutor.getEmail());
                savedTutor.setSubject(tutor.getSubject());
                savedTutor.setDescription(tutor.getDescription());
                savedTutor.setFirstName(tutor.getFirstName());
                savedTutor.setLastName(tutor.getLastName());
                savedTutor.setTelephoneNumber(tutor.getTelephoneNumber());
                savedTutor.setSubject(tutor.getSubject());
                savedTutor.setGender(tutor.getGender());

                tutorRepository.save(savedTutor);
                return savedTutor;
        }

        else {
            throw new IdNotFoundException("Id Not Found Tutor : "+tutorId);
        }


    }

    public void deleteTutor(int tutorId) throws IdNotFoundException{
        Tutor tutor = tutorRepository.findById(tutorId).orElseThrow( () -> new IdNotFoundException("Id Not Found Tutor : "+tutorId));
        tutorRepository.deleteById(tutor.getId());
    }


    public List<Tutor> getBySubject(String subject){
            return tutorRepository.findBySubject(subject);
    }

}
