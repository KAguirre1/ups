package com.example.ups.poo.service;

import com.example.ups.poo.dto.PersonDTO;
import com.example.ups.poo.entity.Person;
import com.example.ups.poo.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    private PersonDTO mapPersonToPersonDTO(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(person.getName() + " " + person.getLastname());
        personDTO.setAge(person.getAge());
        personDTO.setId(person.getPersonId());
        return personDTO;
    }

    private List<PersonDTO> fetchAllPeopleRecords() {
        Iterable<Person> personIterable = personRepository.findAll();
        List<PersonDTO> personDTOList = new ArrayList<>();

        for (Person per : personIterable) {
            PersonDTO personDTO = mapPersonToPersonDTO(per);
            personDTOList.add(personDTO);
        }
        return personDTOList;
    }

    public ResponseEntity getAllPeople() {
        List<PersonDTO> personDTOList = fetchAllPeopleRecords();
        if (personDTOList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person list is empty");
        }
        return ResponseEntity.status(HttpStatus.OK).body(personDTOList);
    }

    public ResponseEntity getPersonById(String id) {
        Optional<Person> personOptional = personRepository.findByPersonId(id);
        if (personOptional.isPresent()) {
            PersonDTO personDTO = mapPersonToPersonDTO(personOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body(personDTO);
        } else {
            String message = "Person with id: " + id + " not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
//        List<PersonDTO> personDTOList = fetchAllPeopleRecords();
//        for (PersonDTO personDTO : personDTOList) {
//            if (id.equalsIgnoreCase(personDTO.getId())) {
//                return ResponseEntity.status(HttpStatus.OK).body(personDTO);
//            }
//        }
//        //String message = "Person with id: " + id + " not found";
//        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body()
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person with id: " + id + " not found");
//    }
//
//    public ResponseEntity createPerson(PersonDTO personDTO) {
//        if (personDTO.getId() == null || personDTO.getId().isBlank()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person Id is required");
//        }
//        if (personDTO.getName() == null || personDTO.getName().isBlank()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person Name is required");
//        }
//        if (personDTO.getLastname() == null || personDTO.getLastname().isBlank()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person Last Name required ");
//        }
//        if (personDTO.getAge() <= 0) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person Age is required");
//        }
//        for (PersonDTO existingPersonDTO : personDTOList) {
//            if (existingPersonDTO.getId().equalsIgnoreCase(personDTO.getId())) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person with the Id: " + personDTO.getId() + " already exists");
//            }
//        }
//        personDTOList.add(personDTO);
//        return ResponseEntity.status(HttpStatus.OK).body("Person successfully register");
//    }
//
//    public  ResponseEntity updatePerson(PersonDTO personDTO) {
//        for (PersonDTO per : personDTOList) {
//            if (personDTO.getId().equalsIgnoreCase(per.getId())) {
//                if (personDTO.getName() != null) {
//                    per.setName(personDTO.getName());
//                }
//                if (personDTO.getLastname() != null) {
//                    per.setLastname(personDTO.getLastname());
//                }
//                if (personDTO.getAge() != 0) {
//                    per.setAge(personDTO.getAge());
//                }
//                return ResponseEntity.status(HttpStatus.OK).body("Person with Id :" + personDTO.getId() + " was successfully");
//            }
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person with Id: " + personDTO.getId() + " not found");
//    }
//
//    public ResponseEntity deletePersonById(String id) {
//        if (id != null && id.length() < 10) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id: " + id + " does not have the required lenght (10 chars min.)");
//        }
//        for (PersonDTO personDTO : personDTOList) {
//            if (id.equalsIgnoreCase(personDTO.getId())) {
//                personDTOList.remove(personDTO);
//                return ResponseEntity.status(HttpStatus.OK).body("Person with id: " + id + " was successfully deleted");
//            }
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person with id: " + id + " was not found");
    }
}