package de.volodymyr.learning.model;



import java.time.LocalDateTime;

public record Task(int id, String description, TaskStatus status, LocalDateTime createdAt,
                   LocalDateTime updatedAt) {
}
