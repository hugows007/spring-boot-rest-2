package br.com.hugows.restwithspringboot.services;

import br.com.hugows.restwithspringboot.converter.DozerConverter;
import br.com.hugows.restwithspringboot.converter.custom.PersonConverter;
import br.com.hugows.restwithspringboot.data.model.Person;
import br.com.hugows.restwithspringboot.exception.ResourceNotFoundException;
import br.com.hugows.restwithspringboot.repository.PersonRepository;
import br.com.hugows.restwithspringboot.vo.PersonVO;
import br.com.hugows.restwithspringboot.vo.PersonVOV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServices {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    private PersonConverter converter;

    public PersonVO findById(Long id) {
        return DozerConverter.parseObject(personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID")), PersonVO.class);
    }

    public List<PersonVO> findAll() {
        return DozerConverter.parseListObjects(personRepository.findAll(), PersonVO.class);
    }

    public PersonVO create(PersonVO person) {
        Person entity = DozerConverter.parseObject(person, Person.class);
        return DozerConverter.parseObject(personRepository.save(entity), PersonVO.class);
    }

    public PersonVOV2 create(PersonVOV2 person) {
        Person entity = converter.convertVOToEntity(person);
        return converter.convertEntityToVO(personRepository.save(entity));
    }


    public PersonVO update(PersonVO person) {
        if(findById(person.getId()) != null) {
            Person entity = DozerConverter.parseObject(person, Person.class);
            return DozerConverter.parseObject(personRepository.save(entity), PersonVO.class);
        }

        return null;
    }

    public void delete(Long id) {
        PersonVO vo = findById(id);
        Person entity = DozerConverter.parseObject(vo, Person.class);
        personRepository.delete(entity);
    }
}
