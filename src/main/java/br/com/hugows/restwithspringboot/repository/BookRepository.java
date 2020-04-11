package br.com.hugows.restwithspringboot.repository;

import br.com.hugows.restwithspringboot.data.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
