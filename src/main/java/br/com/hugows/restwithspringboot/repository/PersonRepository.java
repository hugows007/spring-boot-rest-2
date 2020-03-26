package br.com.hugows.restwithspringboot.repository;

import br.com.hugows.restwithspringboot.data.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
