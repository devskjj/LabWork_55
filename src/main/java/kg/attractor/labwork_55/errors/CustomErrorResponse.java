package kg.attractor.labwork_55.errors;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CustomErrorResponse {
    private String errorMessage;
    private Map<String, List<String>> reasons;
}
