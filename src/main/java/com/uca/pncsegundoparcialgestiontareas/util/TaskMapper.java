package com.uca.pncsegundoparcialgestiontareas.util;

import com.uca.pncsegundoparcialgestiontareas.dto.request.TaskRequestDTO;
import com.uca.pncsegundoparcialgestiontareas.dto.response.TaskResponseDTO;
import com.uca.pncsegundoparcialgestiontareas.entities.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
        public static TaskResponseDTO toResponse(Task task){
            return new TaskResponseDTO()
                    .setId(task.getId())
                    .setTitle(task.getTitle())
                    .setDescription(task.getDescription())
                    .setStatus(task.getStatus());
        }

        public static Task toEntity(TaskRequestDTO taskRequestDTO){
            return Task.builder()
                    .title(taskRequestDTO.getTitle())
                    .description(taskRequestDTO.getDescription())
                    .status(taskRequestDTO.getStatus())
                    .build();
        }
}
