package kg.attractor.labwork_55.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {
    private Integer id;
    @NotNull(message = "Title is mandatory")
    @Size(max = 50, message = "Text length cannot be more than 50")
    private String title;
    @NotNull(message = "Description text is mandatory")
    @Size(max = 255, message = "Description length cannot be more than 255")
    private String description;
    @NotNull(message = "Creator id is mandatory")
    @Positive(message = "Creator id should be positive digit")
    private Integer creatorId;
}
