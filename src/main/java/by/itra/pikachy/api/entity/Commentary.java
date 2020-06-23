package by.itra.pikachy.api.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "commentary")
@Data
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
