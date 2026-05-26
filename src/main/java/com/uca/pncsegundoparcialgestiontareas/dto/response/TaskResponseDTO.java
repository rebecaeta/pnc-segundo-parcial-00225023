package com.uca.pncsegundoparcialgestiontareas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private Integer estimatedHours;
    private Integer loggedHours;
    private LocalDate dueDate;
    private String assignedTo;
    private Boolean active;
}

