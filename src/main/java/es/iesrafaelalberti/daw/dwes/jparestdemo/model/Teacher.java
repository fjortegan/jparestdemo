package es.iesrafaelalberti.daw.dwes.jparestdemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String surname;
    Float level;
    private String imageUrl;
    final boolean doSomething = false;

    @JsonBackReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL)
    Set<Student> tutored = new HashSet<>();

    public Teacher() {
    }

    public Teacher(String name, String surname, Float level) {
        this.name = name;
        this.surname = surname;
        this.level = level;
    }
}
