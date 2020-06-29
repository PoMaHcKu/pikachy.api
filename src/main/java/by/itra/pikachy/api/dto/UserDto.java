package by.itra.pikachy.api.dto;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private int id;
    private String username;
    private String email;
    private SimpleDateFormat created;
    private SimpleDateFormat lastLogin;
    private boolean enabled;
    private List<PostDto> posts = new ArrayList<>();
    private List<String> roles = new ArrayList<>();
}