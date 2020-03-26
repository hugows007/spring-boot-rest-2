package br.com.hugows.restwithspringboot.controller;

import br.com.hugows.restwithspringboot.converter.custom.PersonConverter;
import br.com.hugows.restwithspringboot.services.PersonServices;
import br.com.hugows.restwithspringboot.vo.PersonVO;
import br.com.hugows.restwithspringboot.vo.PersonVOV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices personServices;



    @GetMapping
    public List<PersonVO> findAll() throws Exception {
        return personServices.findAll();
    }

    @GetMapping("/{id}")
    public PersonVO findById(@PathVariable("id") Long id) {
        return personServices.findById(id);
    }

    @PostMapping
    public PersonVO create(@RequestBody PersonVO person) {
        return personServices.create(person);
    }

    @PostMapping("/v2")
    public PersonVOV2 create(@RequestBody PersonVOV2 person) {
        return personServices.create(person);
    }

    @PutMapping
    public PersonVO update(@RequestBody PersonVO person) {
        return personServices.update(person);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        personServices.delete(id);
        return ResponseEntity.ok().build();
    }
}
