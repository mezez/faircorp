package com.emse.spring.faircorp.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DummyUserService implements UserService{

    @Autowired
    public GreetingService greetingService;

    @Override
    public void greetAll() {
        List<String> persons = List.of("Elodie", "Charles");
        for (int i = 0; i < persons.size(); i++) {
            greetingService.greet(persons.get(i));
        }
    }
}
