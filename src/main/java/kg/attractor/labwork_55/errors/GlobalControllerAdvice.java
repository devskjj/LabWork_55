package kg.attractor.labwork_55.errors;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ValidationException;
import kg.attractor.labwork_55.exceptions.FailedToCreateException;
import kg.attractor.labwork_55.services.ErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@Hidden
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final ErrorService errorService;

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomErrorResponse notSuchElementHandler(NoSuchElementException e) {
        log.error("Ошибка запроса: ", e);
        return errorService.makeErrorResponse(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponse validationHandler(MethodArgumentNotValidException e) {
        log.error("Ошибка запроса: ", e);
        return errorService.makeErrorResponse(e.getBindingResult());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponse handleValidation(ValidationException e) {
        log.error("Ошибка запроса: ", e);
        return errorService.makeErrorResponse(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponse handleException(Exception e) {
        log.error("Ошибка запроса: ", e);
        return errorService.makeErrorResponse(e);
    }

    @ExceptionHandler(FailedToCreateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponse handlerFailedToCreateException(Exception e) {
        log.error("Ошибка запроса: ", e);
        return errorService.makeErrorResponse(e);
    }
}
