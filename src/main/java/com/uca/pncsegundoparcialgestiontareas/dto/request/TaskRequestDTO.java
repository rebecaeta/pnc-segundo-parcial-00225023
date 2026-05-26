package com.uca.pncsegundoparcialgestiontareas.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDTO {

    @NotBlank(message = "El título es obligatorio y no puede estar vacío.")
    private String title;

    private String description;

    @NotNull(message = "El estado (status) es obligatorio.")
    private TaskStatus status;

    @NotNull(message = "La prioridad (priority) es obligatoria.")
    private TaskPriority priority;

    @NotNull(message = "Las horas estimadas son obligatorias.")
    @Min(value = 1, message = "Las horas estimadas deben ser al menos 1.")
    private Integer estimatedHours;

    @NotNull(message = "Las horas registradas son obligatorias.")
    @Min(value = 0, message = "Las horas registradas no pueden ser negativas.")
    private Integer loggedHours;

    @NotNull(message = "La fecha de vencimiento es obligatoria.")
    @Future(message = "La fecha de vencimiento debe ser una fecha futura.")
    private LocalDate dueDate;

    @NotBlank(message = "El responsable (assignedTo) es obligatorio.")
    private String assignedTo;
}
