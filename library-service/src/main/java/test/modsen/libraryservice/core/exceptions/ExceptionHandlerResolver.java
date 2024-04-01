package test.modsen.libraryservice.core.exceptions;

import com.library.modsen.core.exceptions.body.ExceptionErrorBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@ResponseBody
public class ExceptionHandlerResolver {
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionErrorBody handleEntityNotFound(BookNotFoundException exception) {
        log.error(exception.getMessage());
        return new ExceptionErrorBody("not found", exception.getMessage());
    }
}
