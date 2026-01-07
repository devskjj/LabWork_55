package kg.attractor.labwork_55.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {
    private Integer id;
    private String title;
    private String description;
    private Integer creatorId;
}
