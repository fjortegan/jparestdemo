package es.iesrafaelalberti.daw.dwes.jparestdemo.repositories;

import es.iesrafaelalberti.daw.dwes.jparestdemo.model.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {
    @Query(value="Select new es.iesrafaelalberti.daw.dwes.jparestdemo.repositories.TutorInfo(t.name, sum(s.age)) " +
            "from Student s inner join Teacher t on t.id = s.tutor.id group by t " +
            "order by sum(s.age) desc")
    List<TutorInfo> ageSumStudents();
}
