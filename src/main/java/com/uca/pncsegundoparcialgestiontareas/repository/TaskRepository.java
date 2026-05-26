package com.uca.pncsegundoparcialgestiontareas.repository;

import com.uca.pncsegundoparcialgestiontareas.entities.Task;
import com.uca.pncsegundoparcialgestiontareas.enums.TaskPriority;
import com.uca.pncsegundoparcialgestiontareas.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByActiveTrue();

    List<Task> findByStatus(TaskStatus status);
    List<Task> findByPriority(TaskPriority priority);
    List<Task> findByStatusAndPriority(TaskStatus status, TaskPriority priority);

    boolean existsByTitleIgnoreCase(String title);
    boolean existsByTitleIgnoreCaseAndIdNot(String title, Long id);
}
