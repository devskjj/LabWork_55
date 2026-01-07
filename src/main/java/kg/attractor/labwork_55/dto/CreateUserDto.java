package kg.attractor.labwork_55.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {
    private String username;
    private String password;
    private String email;
}
