package kg.attractor.labwork_55.services;

import kg.attractor.labwork_55.errors.CustomErrorResponse;
import org.springframework.validation.BindingResult;

public interface ErrorService {
    CustomErrorResponse makeErrorResponse(Exception e);

    CustomErrorResponse makeErrorResponse(BindingResult bindingResult);
}
