package es.iesrafaelalberti.daw.dwes.jparestdemo.controllers;

import es.iesrafaelalberti.daw.dwes.jparestdemo.model.Student;
import es.iesrafaelalberti.daw.dwes.jparestdemo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping(value  = "/students")
    public ResponseEntity<Object> studentList() {
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping(value  = "/students")
    public ResponseEntity<Object> studentAdd(@RequestBody Student student) {
        Optional<Student> oldStudent = studentRepository.findStudentByNameAndSurname(student.getName(),
                                                                                     student.getSurname());
        if(oldStudent.isPresent()) return new ResponseEntity<>("Ese ya existe", HttpStatus.CONFLICT);
        studentRepository.save(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PutMapping(value  = "/students/{id}")
    public ResponseEntity<Object> studentUpdate(@PathVariable("id") Long id, @RequestBody Student student) throws EntityNotFoundException {
        studentRepository.findById(id)
                         .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        studentRepository.save(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @DeleteMapping(value  = "/students/{id}")
    public ResponseEntity<Object> studentDelete(@PathVariable("id") Long id) {
        Optional<Student> oldStudentOpt = studentRepository.findById(id);
        if(oldStudentOpt.isPresent()) { Student oldStudent = oldStudentOpt.get(); } //DRY
        //studentRepository.findById(id)
        //                 .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        studentRepository.deleteById(id);
        return new ResponseEntity<>("Borrada demo " + id, HttpStatus.OK);
    }

}
