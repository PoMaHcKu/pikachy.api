package by.itra.pikachy.api.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private int id;
    private String username;
    private String email;
    private String created;
    private String lastLogin;
    private boolean enabled;
    private List<PostDto> posts;
    private List<String> roles = new ArrayList<>();
}