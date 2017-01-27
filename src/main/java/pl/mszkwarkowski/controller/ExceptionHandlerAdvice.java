package pl.mszkwarkowski.controller;

import org.springframework.http.*;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.*;
import pl.mszkwarkowski.other.Error;

import javax.ws.rs.*;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    /**
     * This method returns information when "JpaObjectRetrievalFailure", "NotFound", "Forbidden" or "BadRequest" exception occurs.
     *
     * @param ex Exception.
     * @return Error object.
     */
    @ExceptionHandler({JpaObjectRetrievalFailureException.class, NotFoundException.class, ForbiddenException.class, BadRequestException.class})
    public ResponseEntity<Error> displayExceptionData(Exception ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        if (ex.getClass() == BadRequestException.class) {
            status = HttpStatus.BAD_REQUEST;
        }
        if (ex.getClass() == ForbiddenException.class) {
            status = HttpStatus.FORBIDDEN;
        }
        Error error = new Error(status.value(), status.getReasonPhrase(), ex.getMessage());
        return ResponseEntity.status(status).body(error);
    }
}
