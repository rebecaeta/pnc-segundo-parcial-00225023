package com.uca.pncsegundoparcialgestiontareas.controller;

import com.uca.pncsegundoparcialgestiontareas.dto.GeneralResponse;
import com.uca.pncsegundoparcialgestiontareas.dto.request.TaskRequestDTO;
import com.uca.pncsegundoparcialgestiontareas.dto.response.TaskResponseDTO;
import com.uca.pncsegundoparcialgestiontareas.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<GeneralResponse> getAllTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority) {

        List<TaskResponseDTO> tasks = taskService.getAllTasks(status, priority);
        return ResponseEntity.ok(
                GeneralResponse.builder()
                        .message("Listado de tareas obtenido correctamente.")
                        .status(200)
                        .data(tasks)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(
                GeneralResponse.builder()
                        .message("Tarea encontrada.")
                        .status(200)
                        .data(taskService.getTaskById(id))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<GeneralResponse> createTask(@Valid @RequestBody TaskRequestDTO request) {
        TaskResponseDTO created = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                GeneralResponse.builder()
                        .message("Tarea creada exitosamente.")
                        .status(201)
                        .data(created)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO request) {

        TaskResponseDTO updated = taskService.updateTask(id, request);
        return ResponseEntity.ok(
                GeneralResponse.builder()
                        .message("Tarea actualizada exitosamente.")
                        .status(200)
                        .data(updated)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
