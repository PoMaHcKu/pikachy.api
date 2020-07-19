package by.itra.pikachy.api.dto;

import lombok.Data;

import java.util.*;

@Data
public class PostDto {
    private int id;
    private String title;
    private String description;
    private int author;
    private String created;
    private String updated;
    private String genre;
    private Set<SectionDto> sections = new TreeSet<>(Comparator.comparing(SectionDto::getPlaceNumber));
    private List<CommentaryDto> commentaries = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
}