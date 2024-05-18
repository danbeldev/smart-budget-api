package ru.pgk.smartbudget.features.exceptionHandler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ValidationExceptionBody {

    private String message = "columns errors";
    private String code;

    private Map<String, String> errors;

    public ValidationExceptionBody(final String code) {
        this.code = code;
    }
}
