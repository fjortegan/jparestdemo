package es.iesrafaelalberti.daw.dwes.jparestdemo.repositories;

import es.iesrafaelalberti.daw.dwes.jparestdemo.model.Role;
import es.iesrafaelalberti.daw.dwes.jparestdemo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;


public interface RoleRepository extends CrudRepository<Role, Long> {
    @Query("Select r from Role r JOIN FETCH r.users where r.id = :id")
    Role findUsers(@Param("id") Long id);
}
