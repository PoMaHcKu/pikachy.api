package by.itra.pikachy.api.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "section")
@Data
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 50)
    @Column(name = "title", nullable = false)
    @Field
    private String title;

    @Size(max = 10000)
    @Column(name = "article")
    @Field
    private String article;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "place_number")
    private int placeNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    @ToString.Exclude
    private Post post;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "section_like",
            joinColumns = {@JoinColumn(name = "section_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> likes = new HashSet<>();
}