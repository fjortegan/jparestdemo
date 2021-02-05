package es.iesrafaelalberti.daw.dwes.jparestdemo.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data // Generate getter, setter of every attribute and toString, equals and hashCode
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private String imageUrl;

    @ManyToOne
    @JoinColumn()
    private Teacher tutor;

    public Student() {
    }

    public Student(String name, String surname, Integer age, Teacher tutor) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.tutor = tutor;
    }
}
