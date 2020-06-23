package by.itra.pikachy.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "section")
@Data
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "count_like")
    private int countLike;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}