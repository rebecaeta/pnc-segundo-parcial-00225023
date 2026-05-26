package com.uca.pncsegundoparcialgestiontareas.service;

import com.uca.pncsegundoparcialgestiontareas.dto.request.TaskRequestDTO;
import com.uca.pncsegundoparcialgestiontareas.dto.response.TaskResponseDTO;
import com.uca.pncsegundoparcialgestiontareas.entities.Task;
import com.uca.pncsegundoparcialgestiontareas.enums.TaskPriority;
import com.uca.pncsegundoparcialgestiontareas.enums.TaskStatus;
import com.uca.pncsegundoparcialgestiontareas.exception.BusinessException;
import com.uca.pncsegundoparcialgestiontareas.exception.ResourceNotFoundException;
import com.uca.pncsegundoparcialgestiontareas.repository.TaskRepository;
import com.uca.pncsegundoparcialgestiontareas.util.TaskMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;


    @Transactional
    public TaskResponseDTO createTask(TaskRequestDTO request) {

        if (request.getStatus() != TaskStatus.PENDING) {
            throw new BusinessException(
                    "El estado inicial debe ser PENDING. No se puede crear en " + request.getStatus() + "."
            );
        }

        if (taskRepository.existsByTitleIgnoreCase(request.getTitle())) {
            throw new BusinessException(
                    "Ya existe una tarea con el título '" + request.getTitle() + "'."
            );
        }

        if (request.getLoggedHours() > request.getEstimatedHours()) {
            throw new BusinessException(
                    "Las horas registradas (" + request.getLoggedHours() +
                            ") no pueden exceder las horas estimadas (" + request.getEstimatedHours() + ")."
            );
        }

        // Regla: dueDate debe ser futura
        if (!request.getDueDate().isAfter(LocalDate.now())) {
            throw new BusinessException(
                    "La fecha de vencimiento debe ser posterior a hoy (" + LocalDate.now() + ")."
            );
        }

        Task task = TaskMapper.toEntity(request);
        Task saved = taskRepository.save(task);
        return TaskMapper.toResponseDTO(saved);
    }


    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAllTasks(TaskStatus status, TaskPriority priority) {
        List<Task> tasks;

        if (status != null && priority != null) {
            tasks = taskRepository.findByStatusAndPriority(status, priority);
        } else if (status != null) {
            tasks = taskRepository.findByStatus(status);
        } else if (priority != null) {
            tasks = taskRepository.findByPriority(priority);
        } else {
            tasks = taskRepository.findAll();
        }

        return tasks.stream()
                .map(TaskMapper::toResponseDTO)
                .toList();
    }
    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea", id));
        return TaskMapper.toResponseDTO(task);
    }


    @Transactional
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea", id));

        if (taskRepository.existsByTitleIgnoreCaseAndIdNot(request.getTitle(), id)) {
            throw new BusinessException(
                    "Ya existe otra tarea con el título '" + request.getTitle() + "'."
            );
        }

        if (request.getLoggedHours() > request.getEstimatedHours()) {
            throw new BusinessException(
                    "Las horas registradas (" + request.getLoggedHours() +
                            ") no pueden exceder las horas estimadas (" + request.getEstimatedHours() + ")."
            );
        }

        if (!request.getDueDate().isAfter(LocalDate.now())) {
            throw new BusinessException(
                    "La fecha de vencimiento debe ser posterior a hoy (" + LocalDate.now() + ")."
            );
        }

        boolean isActive = request.getStatus() != TaskStatus.DONE
                && request.getStatus() != TaskStatus.CANCELLED;

        task.setTitle(request.getTitle().trim());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setEstimatedHours(request.getEstimatedHours());
        task.setLoggedHours(request.getLoggedHours());
        task.setDueDate(request.getDueDate());
        task.setAssignedTo(request.getAssignedTo().trim());
        task.setActive(isActive);

        Task updated = taskRepository.save(task);
        return TaskMapper.toResponseDTO(updated);
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea", id));

        if (task.getStatus() == TaskStatus.IN_PROGRESS || task.getStatus() == TaskStatus.REVIEW) {
            throw new BusinessException(
                    "No se puede eliminar la tarea '" + task.getTitle() +
                            "' porque está en estado " + task.getStatus() + "."
            );
        }

        taskRepository.delete(task);
    }
}
