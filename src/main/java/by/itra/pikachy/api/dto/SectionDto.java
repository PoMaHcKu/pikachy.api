package by.itra.pikachy.api.dto;

import lombok.Data;

@Data
public class SectionDto {
    private int id;
    private String title;
    private int countLike;
    private String article;
    private String imageId;
    private int post;
    private int placeNumber;
}