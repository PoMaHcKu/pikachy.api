package by.itra.pikachy.api.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "section")
@Data
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 50)
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 10000)
    @Column(name = "article")
    private String article;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "count_like")
    private int countLike;

    @Column(name = "place_number")
    private int placeNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    @ToString.Exclude
    private Post post;
}