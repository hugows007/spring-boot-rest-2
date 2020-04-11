package br.com.hugows.restwithspringboot.services;

import br.com.hugows.restwithspringboot.converter.DozerConverter;
import br.com.hugows.restwithspringboot.data.model.Book;
import br.com.hugows.restwithspringboot.data.vo.v1.BookVO;
import br.com.hugows.restwithspringboot.exception.ServiceException;
import br.com.hugows.restwithspringboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookServices {

    @Autowired
    BookRepository bookRepository;

    public BookVO findById(Long id) {
        return DozerConverter.parseObject(bookRepository.findById(id).orElseThrow(() -> ServiceException.builder()
                        .message("No records found for this ID")
                        .build()),
                BookVO.class);
    }

    public Page<BookVO> findAll(Pageable pageable) {
        Page<Book> personList = bookRepository.findAll(pageable);
        return personList.map(this::convertToBookVO);
    }

    private BookVO convertToBookVO(Book book) {
        return DozerConverter.parseObject(book, BookVO.class);
    }

    public BookVO create(BookVO book) {
        Book entity = DozerConverter.parseObject(book, Book.class);
        return DozerConverter.parseObject(bookRepository.save(entity), BookVO.class);
    }

    public BookVO update(BookVO book) {
        if (findById(book.getKey()) != null) {
            Book entity = DozerConverter.parseObject(book, Book.class);
            return DozerConverter.parseObject(bookRepository.save(entity), BookVO.class);
        }

        return null;
    }

    public void delete(Long id) {
        BookVO vo = findById(id);
        Book entity = DozerConverter.parseObject(vo, Book.class);
        bookRepository.delete(entity);
    }
}
