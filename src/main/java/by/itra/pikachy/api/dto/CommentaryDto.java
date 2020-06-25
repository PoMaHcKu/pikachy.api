package by.itra.pikachy.api.dto;

import lombok.Data;

@Data
public class CommentaryDto {
    private int id;
    private String textCommentary;
    private PostDto post;
    private String created;
}