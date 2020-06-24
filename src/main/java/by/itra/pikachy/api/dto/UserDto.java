package by.itra.pikachy.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto {
    private int id;
    private String username;
    private String email;
    private boolean enabled;
    @JsonFormat(pattern = "hh:MM dd-mm-yyyy")
    private LocalDateTime created;
    private List<PostDto> posts;
    private List<String> roles;
}
