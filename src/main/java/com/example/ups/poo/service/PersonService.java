package com.example.ups.poo.service;

import com.example.ups.poo.dto.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PersonService {
    public List<Person> getAllPeople() {
        List<Person> personList = new ArrayList<>();
        Person p1 = new Person("Kerly", "Aguirre", 20, "0944267103");
        Person p2 = new Person("Ruben", "Doblas", 30, "0910452671");
        personList.add(p1);
        personList.add(p2);
        return personList;
    }
}
