package by.itra.pikachy.api.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Formula;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
@Data
@Indexed
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 50)
    @Column(name = "title", nullable = false)
    @Field
    private String title;

    @Size(max = 300)
    @Column(name = "description")
    @Field
    private String description;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @IndexedEmbedded(includePaths = {"article", "title"})
    private List<Section> sections;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @IndexedEmbedded(includePaths = {"textCommentary"})
    private List<Commentary> commentaries;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToMany
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @ManyToMany
    @ToString.Exclude
    @JoinTable(
            name = "post_mark",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "mark_id")}
    )
    private List<Mark> marks;

    @Formula("(select IFNULL(avg(m.mark), 0) from post_mark pm " +
            "inner join mark m on pm.mark_id=m.id where pm.post_id=id)")
    private double rating;
}