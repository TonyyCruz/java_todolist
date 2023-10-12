package br.com.anthonycruz.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository iTaskRepository;

  @PostMapping("/")
  public ResponseEntity create(@RequestBody TaskModel taskModel,
      HttpServletRequest request) {

    LocalDateTime currentDate = LocalDateTime.now();
    if (currentDate.isAfter(taskModel.getStartAt())
        || currentDate.isAfter(taskModel.getEndAt())) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          "The start/end date cannot be before the current date.");
    }

    if (taskModel.getEndAt().isBefore(taskModel.getStartAt())) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          "The end date cannot be before the start date.");
    }

    UUID idUser = (UUID) request.getAttribute("idUser");
    taskModel.setIdUser(idUser);
    TaskModel newTask = this.iTaskRepository.save(taskModel);

    return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
  }

  @GetMapping("/")
  public ResponseEntity<List<TaskModel>> list(HttpServletRequest request) {
    UUID idUser = (UUID) request.getAttribute("idUser");
    List<TaskModel> userTasks = this.iTaskRepository.findByIdUser(idUser);

    return ResponseEntity.status(HttpStatus.OK).body(userTasks);
  }

  @PutMapping("/{id}")
  public TaskModel update(@RequestBody TaskModel taskModel,
      HttpServletRequest request, @PathVariable UUID id) {

    UUID idUser = (UUID) request.getAttribute("idUser");
    taskModel.setId(id);
    taskModel.setIdUser(idUser);
    taskModel.setCreatedAt(LocalDateTime.now());
    iTaskRepository.save(taskModel);

    return taskModel;
  }
}
