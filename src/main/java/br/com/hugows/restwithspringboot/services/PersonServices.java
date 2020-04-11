package br.com.hugows.restwithspringboot.services;

import br.com.hugows.restwithspringboot.converter.DozerConverter;
import br.com.hugows.restwithspringboot.converter.custom.PersonConverter;
import br.com.hugows.restwithspringboot.data.model.Person;
import br.com.hugows.restwithspringboot.data.vo.v1.PersonVO;
import br.com.hugows.restwithspringboot.exception.ServiceException;
import br.com.hugows.restwithspringboot.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonServices {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    private PersonConverter converter;

    public PersonVO findById(Long id) {
        return DozerConverter.parseObject(personRepository.findById(id).orElseThrow(() -> ServiceException.builder()
                        .message("No records found for this ID")
                        .build()),
                PersonVO.class);
    }

    public Page<PersonVO> findPersonByName(String firstName, Pageable pageable) {
        Page<Person> personList = personRepository.findPersonByName(firstName, pageable);
        return personList.map(this::convertToPersonVO);
    }

    public Page<PersonVO> findAll(Pageable pageable) {
        Page<Person> personList = personRepository.findAll(pageable);
        return personList.map(this::convertToPersonVO);
    }

    public br.com.hugows.restwithspringboot.data.vo.v2.PersonVO create(br.com.hugows.restwithspringboot.data.vo.v2.PersonVO person) {
        Person entity = DozerConverter.parseObject(person, Person.class);
        return DozerConverter.parseObject(personRepository.save(entity), br.com.hugows.restwithspringboot.data.vo.v2.PersonVO.class);
    }

    public PersonVO create(PersonVO person) {
        Person entity = converter.convertVOToEntity(person);
        return converter.convertEntityToVO(personRepository.save(entity));
    }


    public PersonVO update(PersonVO person) {
        if (findById(person.getKey()) != null) {
            Person entity = DozerConverter.parseObject(person, Person.class);
            return DozerConverter.parseObject(personRepository.save(entity), PersonVO.class);
        }

        return null;
    }

    @Transactional
    public PersonVO disablePerson(Long id) {
        PersonVO personVO = findById(id);

        if (findById(id) != null) {
            personRepository.disablePerson(id);
            personVO.setEnabled(false);
            Person entity = DozerConverter.parseObject(personVO, Person.class);
            return DozerConverter.parseObject(personRepository.save(entity), PersonVO.class);
        }

        return null;
    }

    public void delete(Long id) {
        PersonVO vo = findById(id);
        Person entity = DozerConverter.parseObject(vo, Person.class);
        personRepository.delete(entity);
    }

    private PersonVO convertToPersonVO(Person person) {
        return DozerConverter.parseObject(person, PersonVO.class);
    }
}
