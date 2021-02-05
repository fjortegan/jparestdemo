package es.iesrafaelalberti.daw.dwes.jparestdemo.bootstrap;

import es.iesrafaelalberti.daw.dwes.jparestdemo.model.Role;
import es.iesrafaelalberti.daw.dwes.jparestdemo.model.Student;
import es.iesrafaelalberti.daw.dwes.jparestdemo.model.Teacher;
import es.iesrafaelalberti.daw.dwes.jparestdemo.model.User;
import es.iesrafaelalberti.daw.dwes.jparestdemo.repositories.RoleRepository;
import es.iesrafaelalberti.daw.dwes.jparestdemo.repositories.StudentRepository;
import es.iesrafaelalberti.daw.dwes.jparestdemo.repositories.TeacherRepository;
import es.iesrafaelalberti.daw.dwes.jparestdemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Seeder implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String[] args) {
        Teacher p1 = teacherRepository.save(new Teacher("Juan", "Lopez", 6.2f));
        Teacher p2 = teacherRepository.save(new Teacher("Javier", "Ortega", 0.2f));
        Student s1 = studentRepository.save(new Student("Juan", "Perez", 5, p1));
        Student s2 = studentRepository.save(new Student("Juana", "Pereza", 7, p2));
        Student s3 = studentRepository.save(new Student("Ruben", "Orco", 6, p2));
        Role r1 = roleRepository.save(new Role("ROLE_ADMIN"));
        Role r2 = roleRepository.save(new Role("ROLE_USER"));
        Role r3 = roleRepository.save(new Role("ROLE_GOD"));
        User u1 = userRepository.save(new User("rubo", "quetedoy", r1));
        User u2 = userRepository.save(new User("tere", "tere", r2));
        User u3 = userRepository.save(new User("javier", "pestillo", r3));
        System.out.println("");
    }
}
