package by.itra.pikachy.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDto {
    private String username;
    private String token;
}