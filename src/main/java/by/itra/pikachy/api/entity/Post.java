package by.itra.pikachy.api.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 300)
    @Column(name = "description")
    private String description;

    @Column(name = "mark")
    private int mark;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "post")
    private List<Section> sections;

    @OneToMany(mappedBy = "post")
    private List<Commentary> commentaries;

    @ManyToMany
    @JoinTable(name = "post_genre",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "gene_id"))
    private List<Genre> genres;

    public Post() {
        this.genres = new ArrayList<>();
    }
}