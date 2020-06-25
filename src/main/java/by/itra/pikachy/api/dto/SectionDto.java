package by.itra.pikachy.api.dto;

import by.itra.pikachy.api.entity.Post;
import lombok.Data;

@Data
public class SectionDto {
    private int id;
    private String title;
    private int countLike;
    private Post post;
}
