package br.com.anthonycruz.todolist.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository iTaskRepository;

  @PostMapping("/")
  public ResponseEntity<TaskModel> create(@RequestBody TaskModel taskModel) {
    TaskModel newTask = this.iTaskRepository.save(taskModel);

    return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
  }
}
