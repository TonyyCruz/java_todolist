package br.com.anthonycruz.todolist.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.anthonycruz.todolist.task.TaskException;
import br.com.anthonycruz.todolist.user.UserException;

@ControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(TaskException.class)
  public ResponseEntity<String> handleTaskException(TaskException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }

  @ExceptionHandler(UserException.class)
  public ResponseEntity<String> handleUserException(UserException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }
}
