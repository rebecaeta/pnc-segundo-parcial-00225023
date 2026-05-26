package com.uca.pncsegundoparcialgestiontareas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Task")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TaskPriority priority;

    @Column(name = "estimatedHours")
    private Integer estimatedHours;

    @Column(name = "loggedHours")
    private Integer loggedHours;

    @Column(name = "dueDate")
    private LocalDate dueDate;

    @Column(name = "assignedTo")
    private String assignedTo;

    @Column(name = "active")
    private Boolean active;



}
