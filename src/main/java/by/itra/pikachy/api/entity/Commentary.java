package by.itra.pikachy.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "commentary")
@Data
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "text_commentary")
    private String textCommentary;
    @Column(name = "created")
    private LocalDateTime created;
    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "author_id", nullable = false)
    private User user;
}