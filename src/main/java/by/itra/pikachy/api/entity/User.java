package by.itra.pikachy.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usr")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    @Column(name = "enabled")
    private boolean enabled;
    @OneToMany(mappedBy = "user")
    private List<Post> posts;
    @ManyToMany(mappedBy = "users")
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;
    @Column(name = "token")
    private String verificationToken;

    public User() {
        this.roles = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.created = LocalDateTime.now();
    }
}