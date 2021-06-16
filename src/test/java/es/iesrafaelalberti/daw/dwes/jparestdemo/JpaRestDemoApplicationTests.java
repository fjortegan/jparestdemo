package es.iesrafaelalberti.daw.dwes.jparestdemo;

import es.iesrafaelalberti.daw.dwes.jparestdemo.repositories.RoleRepository;
import es.iesrafaelalberti.daw.dwes.jparestdemo.repositories.StudentRepository;
import es.iesrafaelalberti.daw.dwes.jparestdemo.repositories.TeacherRepository;
import es.iesrafaelalberti.daw.dwes.jparestdemo.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaRestDemoApplicationTests {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;


    @BeforeAll
    void sampleData() {
        // A: arrange data for testing

    }

    @AfterEach

    @Test
    void contextLoads() {
        // Just for test purposes, not for definitive version
        assert studentRepository.count() == 3;
        assert teacherRepository.count() == 2;
        assert roleRepository.count() == 3;
        assert userRepository.count() == 3;
    }

    @Test
    void studentController() {

    }

}
