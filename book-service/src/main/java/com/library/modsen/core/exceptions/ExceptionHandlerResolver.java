package com.library.modsen.core.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import test.modsen.library.core.exceptions.body.ExceptionErrorBody;
import test.modsen.library.core.exceptions.exceptions.CustomEntityNotFoundException;
import test.modsen.library.core.exceptions.exceptions.CustomValidationException;

@Slf4j
@ResponseBody
@RestControllerAdvice
public class ExceptionHandlerResolver {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomEntityNotFoundException.class)
    public ExceptionErrorBody handleEntityNotFound(CustomEntityNotFoundException exception) {
        log.error(exception.getMessage());
        return new ExceptionErrorBody("error", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalAccessError.class)
    public ExceptionErrorBody dataAccessError(IllegalAccessError exception) {
        log.error(exception.getMessage());
        return new ExceptionErrorBody("error",
                "Запрос отклонен. Сервер не может обработать данные. Обратитесь к Админу");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomValidationException.class)
    public ExceptionErrorBody validationError(CustomValidationException exception) {
        log.error(exception.getMessage());
        return new ExceptionErrorBody("error",
                "Запрос не валиден. Перепроверьте данные и повторите попытку");
    }
}
