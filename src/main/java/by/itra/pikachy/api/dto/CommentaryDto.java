package by.itra.pikachy.api.dto;

import by.itra.pikachy.api.entity.Post;
import lombok.Data;

@Data
public class CommentaryDto {
    private int id;
    private Post post;
}
