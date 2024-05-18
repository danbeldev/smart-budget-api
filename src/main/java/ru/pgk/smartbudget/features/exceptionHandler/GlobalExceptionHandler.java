package ru.pgk.smartbudget.features.exceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import ru.pgk.smartbudget.common.exceptions.BadRequestException;
import ru.pgk.smartbudget.common.exceptions.ForbiddenException;
import ru.pgk.smartbudget.common.exceptions.ResourceNotFoundException;
import ru.pgk.smartbudget.features.exceptionHandler.dto.ExceptionBody;
import ru.pgk.smartbudget.features.exceptionHandler.dto.ValidationExceptionBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFound(final ResourceNotFoundException e) {
        return new ExceptionBody(e.getMessage(), "resource_not_found_error");
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleForbidden(final ForbiddenException e) {
        return new ExceptionBody(e.getMessage(), "forbidden_error");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleDataIntegrityViolation(final DataIntegrityViolationException e) {
        return new ExceptionBody(e.getMessage(), "data_integrity_violation_error");
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleBadRequestException(final BadRequestException e) {
        return new ExceptionBody(e.getMessage(), "bad_request_error");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionBody handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e
    ) {
        ValidationExceptionBody body = new ValidationExceptionBody("validation_error");
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        body.setErrors(errors.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return body;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionBody handleConstraintViolation(
            final ConstraintViolationException e
    ) {
        ValidationExceptionBody body = new ValidationExceptionBody("validation_error");
        body.setErrors(e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                )));
        return body;
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleValidationExceptions(HandlerMethodValidationException e) {
        Optional<? extends MessageSourceResolvable> messageSourceResolvable = e.getAllErrors().stream().findFirst();
        return new ExceptionBody(messageSourceResolvable.isPresent()
                ? messageSourceResolvable.get().getDefaultMessage() : "", "validation_param_error");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return new ExceptionBody(e.getMessage(), "missing_request_parameter_error");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleException(final Exception e) {
        return new ExceptionBody(e.getMessage(), "internal_error");
    }
}
