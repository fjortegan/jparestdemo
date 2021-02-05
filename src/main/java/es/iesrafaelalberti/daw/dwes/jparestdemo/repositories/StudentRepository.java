package es.iesrafaelalberti.daw.dwes.jparestdemo.repositories;

import es.iesrafaelalberti.daw.dwes.jparestdemo.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Optional<Student> findStudentByNameAndSurname(String name, String Surname);
}
