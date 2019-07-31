package my.test.azoft.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Entity has already been added")
public class AlreadyExistException extends RuntimeException {
}
