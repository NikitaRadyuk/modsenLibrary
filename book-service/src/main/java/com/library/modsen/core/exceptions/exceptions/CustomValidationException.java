package com.library.modsen.core.exceptions.exceptions;

public class CustomValidationException extends IllegalArgumentException{
    public CustomValidationException(){
        super("Запрос не подтвержден. Повторите попытку");
    }
}
