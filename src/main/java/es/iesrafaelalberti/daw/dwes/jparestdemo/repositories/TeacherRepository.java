package es.iesrafaelalberti.daw.dwes.jparestdemo.repositories;

import es.iesrafaelalberti.daw.dwes.jparestdemo.model.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {

}
