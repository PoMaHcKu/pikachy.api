package by.itra.pikachy.api.dto;

import lombok.Data;

@Data
public class CommentaryDto {
    private int id;
    private String textCommentary;
    private int post;
    private String created;
    private UserDto user;
}