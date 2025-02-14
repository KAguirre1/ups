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

    private Person mapPersonDTOToPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setPersonId(personDTO.getId());
        String name = personDTO.getName();
        String[] fullNameArray = name.split(" ");
        person.setName(fullNameArray[0]);
        person.setLastname(fullNameArray[1]);
        person.setAge(personDTO.getAge());
        return person;
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
    }

    public ResponseEntity createPerson(PersonDTO personDTO) {
        if (personDTO.getId() == null || personDTO.getId().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person Id is required");
        } else if (personDTO.getId().length() != 10) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person Id must be 10 digits");
        } else if (personDTO.getName() == null || personDTO.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person Name is required");
        } else if (personDTO.getName().split(" ").length == 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The full Name is required");
        } else if (personDTO.getAge() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person Age is required");
        }
        Optional<Person> personOptional = personRepository.findByPersonId(personDTO.getId());
        if (personOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person with the Id: " + personDTO.getId() + " already exists");
        }
        Person person = mapPersonDTOToPerson(personDTO);

        personRepository.save(person);
        return ResponseEntity.status(HttpStatus.OK).body("Person successfully register");
    }

    public ResponseEntity updatePerson(PersonDTO personDTO) {
        Optional<Person> personOptional = personRepository.findByPersonId(personDTO.getId());
        if (!personOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person with Id: " + personDTO.getId() + " not found");
        }
        Person per = personOptional.get();
        if (personDTO.getName() == null && personDTO.getAge() == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("No changes made for person with Id: " + personDTO.getId());
        }
        if (personDTO.getName() != null && !personDTO.getName().trim().isEmpty()) {
            String fullName = personDTO.getName();
            if (fullName.contains(" ")) {
                String[] fullNameArray = fullName.split(" ");
                per.setName(fullNameArray[0]);
                per.setLastname(fullNameArray[1]);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Both first and last names are required");
            }
        } else if (personDTO.getName() != null && personDTO.getName().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Both first and last names are required");
        }
        if (personDTO.getAge() > 0) {
            per.setAge(personDTO.getAge());
        } else if (personDTO.getAge() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Age cannot be negative");
        }
        personRepository.save(per);
        return ResponseEntity.status(HttpStatus.OK).body("Person with Id: " + personDTO.getId() + " was successfully updated");
    }

    public ResponseEntity deletePersonById(String id) {
        Optional<Person> personOptional = personRepository.findByPersonId(id);
        if (id != null && id.length() < 10) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id: " + id + " does not have the required length (10 chars min.)");
        }
        if (personOptional.isPresent()) {
            if (id.equalsIgnoreCase(personOptional.get().getPersonId())) {
                personRepository.delete(personOptional.get());
                return ResponseEntity.status(HttpStatus.OK).body("Person with id: " + id + " was successfully deleted");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person with id: " + id + " was not found");
    }
}