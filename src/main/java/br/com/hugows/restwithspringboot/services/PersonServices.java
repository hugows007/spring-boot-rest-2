package br.com.hugows.restwithspringboot.services;

import br.com.hugows.restwithspringboot.model.Person;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonServices {
    private final AtomicLong idSimulated = new AtomicLong();

    public Person findById(String id) {
        Person person = new Person(
                idSimulated.incrementAndGet(),
                "Hugo",
                "Bezerra Pimentel",
                "Rua Bom Jardim",
                "Male"

        );

        return person;
    }
}
