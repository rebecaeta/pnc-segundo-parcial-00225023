package com.uca.pncsegundoparcialgestiontareas.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiError {
    private String message;
    private int code;
    private Object errors;
}
