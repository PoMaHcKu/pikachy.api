package by.itra.pikachy.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mark")
@Data
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mark")
    private int mark;

    @ManyToMany
    @JoinTable(
            name = "user_mark",
            joinColumns = @JoinColumn(name = "mark_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;
}