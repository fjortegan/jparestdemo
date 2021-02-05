package es.iesrafaelalberti.daw.dwes.jparestdemo.controllers;

import es.iesrafaelalberti.daw.dwes.jparestdemo.model.Teacher;
import es.iesrafaelalberti.daw.dwes.jparestdemo.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
public class TeacherController {
    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping(value = "/teachers")
    public ResponseEntity<Object> teacherList() {
        return new ResponseEntity<>(teacherRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/teachers/{id}")
    public ResponseEntity<Object> teacherDetail(@PathVariable("id") Long id) {
        Teacher myTeacher = teacherRepository.findById(id)
                                             .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        return new ResponseEntity<>( new Object[] { myTeacher, myTeacher.getTutored() } , HttpStatus.OK);
    }
}
