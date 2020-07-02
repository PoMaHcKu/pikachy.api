package by.itra.pikachy.api.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genre")
@Data
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "genre_name")
    private String genreName;

    @ToString.Exclude
    @OneToMany(mappedBy = "genre")
    private List<Post> posts = new ArrayList<>();
}