package es.iesrafaelalberti.daw.dwes.jparestdemo.repositories;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TutorInfo {
    String name;
    Long ageSum;

    public TutorInfo() {
    }

    public TutorInfo(String name, Long ageSum) {
        this.name = name;
        this.ageSum = ageSum;
    }
}
