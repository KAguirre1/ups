package com.example.ups.poo.config;

import com.example.ups.poo.entity.Person;
import com.example.ups.poo.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component

public class BootStrapData implements CommandLineRunner {

    private final PersonRepository personRepository;

    public BootStrapData(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Person p1 = new Person();
        p1.setName("Ruben");
        p1.setLastname("Doblas");
        p1.setAge(30);
        p1.setPersonId("0944267103");

        Person p2 = new Person();
        p2.setName("Irina");
        p2.setLastname("Isaias");
        p2.setAge(31);
        p2.setPersonId("0944267177");

        personRepository.save(p1);
        personRepository.save(p2);

        System.out.println("--- Sarted BootStrapData ---");
        System.out.println("");
    }
}
