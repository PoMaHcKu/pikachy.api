package by.itra.pikachy.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "tag")
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "tag_name")
    private String tagName;
    @ManyToMany(mappedBy = "tags")
    private List<Post> posts = new ArrayList<>();
}
