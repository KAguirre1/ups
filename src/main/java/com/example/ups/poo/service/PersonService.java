package com.example.ups.poo.service;

import com.example.ups.poo.dto.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    List<Person> personList = new ArrayList<>();

        public List<Person> getAllPeople() {
            Person p1 = new Person();
            p1.setName("Kerly");
            p1.setLastname("Aguirre");
            p1.setAge(20);
            p1.setId("0944267103");

            Person p2 = new Person("Ruben", "Doblas", 30, "0910452671");

            personList.add(p1);
            personList.add(p2);

            return personList;
    }
}
