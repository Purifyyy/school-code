package sk.stuba.fei.uim.oop.assignment3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongRequestException extends RuntimeException{
}
