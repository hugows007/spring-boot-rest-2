package br.com.hugows.restwithspringboot.repository;

import br.com.hugows.restwithspringboot.data.model.Person;
import br.com.hugows.restwithspringboot.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(@Param("userName") String userName);

}
