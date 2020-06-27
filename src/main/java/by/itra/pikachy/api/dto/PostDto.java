package by.itra.pikachy.api.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostDto {
    private int id;
    private String title;
    private String description;
    private int mark;
    private int user;
    private List<SectionDto> sections = new ArrayList<>();
    private List<CommentaryDto> commentaries = new ArrayList<>();
    private List<String> genres = new ArrayList<>();
}