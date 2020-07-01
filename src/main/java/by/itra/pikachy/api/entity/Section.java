package by.itra.pikachy.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "section")
@Setter
@Getter
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
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

    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}