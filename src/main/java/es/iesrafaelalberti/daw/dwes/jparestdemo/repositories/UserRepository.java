package es.iesrafaelalberti.daw.dwes.jparestdemo.repositories;

import es.iesrafaelalberti.daw.dwes.jparestdemo.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
