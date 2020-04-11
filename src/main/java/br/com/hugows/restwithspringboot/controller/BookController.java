package br.com.hugows.restwithspringboot.controller;

import br.com.hugows.restwithspringboot.data.vo.v1.BookVO;
import br.com.hugows.restwithspringboot.services.BookServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//@CrossOrigin // Habilitando apenas para um controller
@Api(value = "Book Endpoint", description = "Description for book", tags = {"Book Endpoint"})
@RestController
@RequestMapping("/api/book/v1")
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private BookServices bookServices;

    private final PagedResourcesAssembler<BookVO> assembler;

    @ApiOperation(value = "Find all book recorded")
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "12") int limite,
                                     @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limite, Sort.by(sortDirection, "title"));

        Page<BookVO> books = bookServices.findAll(pageable);

        books.forEach(
                p -> {
                    p.add(linkTo(methodOn(BookController.class)
                            .findById(p.getKey()))
                            .withSelfRel());
                }
        );
        PagedModel<?> pagedModel = assembler.toModel(books);

        return ResponseEntity.ok(pagedModel);
    }

    @ApiOperation(value = "Find book by id")
    @GetMapping(value = "/{id}")
    public BookVO findById(@PathVariable("id") Long id) {
        BookVO bookVO = bookServices.findById(id);

        bookVO.add(linkTo(methodOn(BookController.class)
                .findById(id))
                .withSelfRel());

        return bookVO;
    }

    @ApiOperation(value = "Create a one book")
    @PostMapping
    public BookVO create(@RequestBody BookVO book) {
        BookVO bookVO = bookServices.create(book);

        bookVO.add(linkTo(methodOn(BookController.class)
                .findById(bookVO.getKey()))
                .withSelfRel());

        return bookVO;
    }

    @ApiOperation(value = "Update a one book")
    @PutMapping
    public BookVO update(@RequestBody BookVO book) {
        BookVO bookVO = bookServices.update(book);

        bookVO.add(linkTo(methodOn(BookController.class)
                .findById(bookVO.getKey()))
                .withSelfRel());

        return bookVO;
    }

    @ApiOperation(value = "Delete a one book by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        bookServices.delete(id);
        return ResponseEntity.ok().build();
    }
}
