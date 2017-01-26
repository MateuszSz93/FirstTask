package pl.mszkwarkowski.controller;

import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.*;
import pl.mszkwarkowski.other.Error;

import javax.ws.rs.*;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * This method returns information when "BadRequest" exception occurs.
     *
     * @param ex BadRequestException.
     * @return Error object.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public Error displayBadRequestMessage(BadRequestException ex) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
        return error;
    }

    /**
     * This method returns information when "Forbidden" exception occurs.
     *
     * @param ex ForbiddenException.
     * @return Error object.
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public Error displayForbiddenMessage(ForbiddenException ex) {
        Error error = new Error(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), ex.getMessage());
        return error;
    }

    /**
     * This method returns information when "JpaObjectRetrievalFailure" or "NotFoundException" exception occurs.
     *
     * @param ex Exception.
     * @return Error object.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({JpaObjectRetrievalFailureException.class, NotFoundException.class})
    public Error displayNotFoundMessage(Exception ex) {
        Error error = new Error(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
        return error;
    }
}
