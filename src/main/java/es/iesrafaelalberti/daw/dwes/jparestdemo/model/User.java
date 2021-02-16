package es.iesrafaelalberti.daw.dwes.jparestdemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String username;
    String password;
    String imageUrl;
    String token;
    @ManyToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String password, Role rol) {
        this.username = username;
        this.password = new BCryptPasswordEncoder().encode(password);
        roles.add(rol);
        rol.getUsers().add(this);
    }
}
