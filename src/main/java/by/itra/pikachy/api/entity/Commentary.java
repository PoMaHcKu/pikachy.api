package by.itra.pikachy.api.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "commentary")
@Data
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(max = 1000)
    @Column(name = "text_commentary")
    private String textCommentary;

    @Column(name = "created")
    private LocalDateTime created;

    @NotBlank
    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @NotBlank
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "author_id", nullable = false)
    private User user;
}