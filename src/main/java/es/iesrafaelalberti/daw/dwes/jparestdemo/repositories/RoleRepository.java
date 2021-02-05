package es.iesrafaelalberti.daw.dwes.jparestdemo.repositories;

import es.iesrafaelalberti.daw.dwes.jparestdemo.model.Role;
import org.springframework.data.repository.CrudRepository;


public interface RoleRepository extends CrudRepository<Role, Long> {

}
