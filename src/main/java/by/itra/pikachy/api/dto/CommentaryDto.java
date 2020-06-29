package by.itra.pikachy.api.dto;

import lombok.Data;

import java.text.SimpleDateFormat;

@Data
public class CommentaryDto {
    private int id;
    private String textCommentary;
    private int post;
    private SimpleDateFormat created;
    private UserDto user;
}