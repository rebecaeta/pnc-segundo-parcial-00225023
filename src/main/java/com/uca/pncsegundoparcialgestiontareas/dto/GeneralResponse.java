package com.uca.pncsegundoparcialgestiontareas.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralResponse {
    private String uri;
    private String message;
    private int status;
    private Object data;
}
