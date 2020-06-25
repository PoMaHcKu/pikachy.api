package by.itra.pikachy.api.dto;

import by.itra.pikachy.api.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class PostDto {
    private int id;
    private String title;
    private String description;
    private int mark;
    private User user;
    private List<SectionDto> sections;
    private List<CommentaryDto> commentaries;
    private List<String> genres;
}