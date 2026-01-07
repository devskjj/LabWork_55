package kg.attractor.labwork_55.services.impl;

import kg.attractor.labwork_55.errors.CustomErrorResponse;
import kg.attractor.labwork_55.services.ErrorService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.*;

@Service
public class ErrorServiceImpl implements ErrorService {

    @Override
    public CustomErrorResponse makeErrorResponse(Exception e) {
        String message = e.getMessage();
        CustomErrorResponse errorResponse = new CustomErrorResponse();
        errorResponse.setErrorMessage(message);
        errorResponse.setReasons(Map.of("errors", List.of(message)));
        return errorResponse;
    }

    @Override
    public CustomErrorResponse makeErrorResponse(BindingResult bindingResult) {
        Map<String, List<String>> reasons = new HashMap<>();
        bindingResult.getFieldErrors().stream().
                filter(err -> err.getDefaultMessage() != null).
                forEach(e -> {
                    List<String> errors = new ArrayList<>();
                    errors.add(e.getDefaultMessage());
                    if (!reasons.containsKey(e.getField())) {
                        reasons.put(e.getField(), errors);
                    }
                });

        CustomErrorResponse errorResponse = new CustomErrorResponse();
        errorResponse.setErrorMessage("Validation error");
        errorResponse.setReasons(reasons);
        return errorResponse;
    }
}
