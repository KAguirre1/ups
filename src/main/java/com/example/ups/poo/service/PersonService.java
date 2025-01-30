package com.example.ups.poo.service;

import com.example.ups.poo.dto.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    List<Person> personList = new ArrayList<>();

    public ResponseEntity getAllPeople() {
        if (personList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person list is empty");
        }
        return ResponseEntity.status(HttpStatus.OK).body(personList);
    }

    public ResponseEntity getPersonById(String id) {
        for (Person person: personList) {
            if (id.equalsIgnoreCase(person.getId())) {
                return ResponseEntity.status(HttpStatus.OK).body(person);
            }
        }
        //String message = "Person with id: " + id + " not found";
        //return ResponseEntity.status(Http.Status.NOT_FOUND).body()
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person with id: " + id + " not found");
    }

    public ResponseEntity createPerson(Person person) {
        if (person.getId() == null || person.getId().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person Id is required");
        }
        if (person.getName() == null || person.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person Name is required");
        }
        if (person.getLastname() == null || person.getLastname().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person Last Name required ");
        }
        if (person.getAge() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person Age is required");
        }
        for (Person existingPerson : personList) {
            if (existingPerson.getId().equalsIgnoreCase(person.getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person with the Id: " + person.getId() + " already exists");
            }
        }
        personList.add(person);
        return ResponseEntity.status(HttpStatus.OK).body("Person successfully register");
    }
}