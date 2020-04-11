package br.com.hugows.restwithspringboot.converter.custom;

import br.com.hugows.restwithspringboot.data.model.Person;
import br.com.hugows.restwithspringboot.data.vo.v1.PersonVO;
import org.springframework.stereotype.Service;

@Service
public class PersonConverter {
    public PersonVO convertEntityToVO(Person person){
        return PersonVO.builder()
                .key(person.getId())
                .address(person.getAddress())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .gener(person.getGener())
                .enabled(person.getEnabled())
                .build();
    }

    public Person convertVOToEntity(PersonVO person){
        return Person.builder()
                .id(person.getKey())
                .address(person.getAddress())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .gener(person.getGener())
                .enabled(person.getEnabled())
                .build();
    }
}
