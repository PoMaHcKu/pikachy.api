package by.itra.pikachy.api.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Table(name = "tag")
@Data
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tag_name")
    private String tagName;

    @ToString.Exclude
    @ManyToMany(mappedBy = "tags")
    private List<Post> users;
}
