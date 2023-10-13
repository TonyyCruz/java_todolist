package br.com.anthonycruz.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @Column(length = 50)
  private String title;

  private String description;
  private String priority;
  private LocalDateTime startAt;
  private LocalDateTime endAt;

  private UUID idUser;

  @CreationTimestamp
  private LocalDateTime createdAt;

  public void setTitle(String title) throws TaskException {
    if (title.length() > 50) {
      throw new TaskException("Title must have less than 50 characters.");
    }

    this.title = title;
  }

  public void setStartAt(LocalDateTime date) throws TaskException {
    LocalDateTime currentDate = LocalDateTime.now();

    if (currentDate.isAfter(date)) {
      throw new TaskException("The start date cannot be before the current date.");
    }

    this.startAt = date;
  }

  public void setEndAt(LocalDateTime date) throws TaskException {
    LocalDateTime currentDate = LocalDateTime.now();

    if (currentDate.isAfter(date)) {
      throw new TaskException("The end date cannot be before the current date.");
    }

    this.endAt = date;
  }
}
