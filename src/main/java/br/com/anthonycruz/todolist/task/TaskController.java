package br.com.anthonycruz.todolist.task;

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

import br.com.anthonycruz.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/")
  public ResponseEntity<TaskModel> create(@RequestBody TaskModel taskModel,
      HttpServletRequest request) {

    if (taskModel.getEndAt().isBefore(taskModel.getStartAt())) {
      throw new TaskException("The end date cannot be before the start date.");
    }

    UUID idUser = (UUID) request.getAttribute("idUser");
    taskModel.setIdUser(idUser);
    TaskModel newTask = this.taskRepository.save(taskModel);

    return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
  }

  @GetMapping("/")
  public ResponseEntity<List<TaskModel>> list(HttpServletRequest request) {
    UUID idUser = (UUID) request.getAttribute("idUser");
    List<TaskModel> userTasks = this.taskRepository.findByIdUser(idUser);

    return ResponseEntity.status(HttpStatus.OK).body(userTasks);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TaskModel> update(@RequestBody TaskModel taskModel,
      HttpServletRequest request, @PathVariable UUID id) {

    if (taskModel.getEndAt().isBefore(taskModel.getStartAt())) {
      throw new TaskException("The end date cannot be before the start date.");
    }

    TaskModel task = taskRepository.findById(id).orElse(null);

    if (task == null) {
      throw new TaskException("The task does not exists.");
    }

    UUID idUser = (UUID) request.getAttribute("idUser");
    if (!task.getIdUser().equals(idUser)) {
      throw new TaskException("You have no permission to update this task.");
    }

    Utils.copyNotNullProperties(taskModel, task);

    TaskModel updatedTask = taskRepository.save(task);
    return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
  }
}
