package br.com.hugows.restwithspringboot.controller;

import br.com.hugows.restwithspringboot.data.vo.v1.PersonVO;
import br.com.hugows.restwithspringboot.services.PersonServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//@CrossOrigin // Habilitando apenas para um controller
@Api(value = "Person Endpoint", description = "Description for person", tags = {"Person Endpoint"})
@RestController
@RequestMapping("/api/person/v1")
@RequiredArgsConstructor
public class PersonController {

    @Autowired
    private PersonServices personServices;

    private final PagedResourcesAssembler<PersonVO> assembler;

    @ApiOperation(value = "Find all person recorded")
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "12") int limite,
                                     @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limite, Sort.by(sortDirection, "firstName"));

        Page<PersonVO> persons = personServices.findAll(pageable);
        persons.forEach(
                p -> {
                    p.add(linkTo(methodOn(PersonController.class)
                            .findById(p.getKey()))
                            .withSelfRel());
                }
        );

        PagedModel<?> pagedModel = assembler.toModel(persons);

        return ResponseEntity.ok(pagedModel);
    }

    @ApiOperation(value = "Find person by like name")
    @GetMapping(value = "/findPersonByName/{firstName}")
    public ResponseEntity<?> findPersonByName(@PathVariable("firstName") String firstName,
                                              @RequestParam(value = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "limit", defaultValue = "12") int limite,
                                              @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limite, Sort.by(sortDirection, "firstName"));

        Page<PersonVO> persons = personServices.findPersonByName(firstName, pageable);
        persons.forEach(
                p -> {
                    p.add(linkTo(methodOn(PersonController.class)
                            .findById(p.getKey()))
                            .withSelfRel());
                }
        );

        PagedModel<?> pagedModel = assembler.toModel(persons);
        return ResponseEntity.ok(pagedModel);
    }

    @ApiOperation(value = "Find person by id")
    @GetMapping(value = "/{id}")
    public PersonVO findById(@PathVariable("id") Long id) {
        PersonVO personVO = personServices.findById(id);

        personVO.add(linkTo(methodOn(PersonController.class)
                .findById(id))
                .withSelfRel());

        return personVO;
    }

    @ApiOperation(value = "Create a one person")
    @PostMapping
    public PersonVO create(@RequestBody PersonVO person) {
        PersonVO personVO = personServices.create(person);

        personVO.add(linkTo(methodOn(PersonController.class)
                .findById(personVO.getKey()))
                .withSelfRel());

        return personVO;
    }

    @ApiOperation(value = "Update a one person")
    @PutMapping
    public PersonVO update(@RequestBody PersonVO person) {
        PersonVO personVO = personServices.update(person);

        personVO.add(linkTo(methodOn(PersonController.class)
                .findById(personVO.getKey()))
                .withSelfRel());

        return personVO;
    }

    @ApiOperation(value = "Disable a specific person by yout ID")
    @PatchMapping(value = "/{id}")
    public PersonVO disablePerson(@PathVariable("id") Long id) {
        PersonVO personVO = personServices.disablePerson(id);

        personVO.add(linkTo(methodOn(PersonController.class)
                .findById(id))
                .withSelfRel());

        return personVO;
    }

    @ApiOperation(value = "Delete a one person by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        personServices.delete(id);
        return ResponseEntity.ok().build();
    }
}
