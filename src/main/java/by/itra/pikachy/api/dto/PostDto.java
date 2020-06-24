package by.itra.pikachy.api.dto;

import by.itra.pikachy.api.entity.Commentary;
import lombok.Data;

import java.util.List;

@Data
public class PostDto {
    private int id;
    private String title;
    private String description;
    private int mark;
    private int user_id;
    private List<SectionDto> sections;
    private List<String> tags;
    private List<String> genres;
    private List<CommentaryDto> commentaries;
}