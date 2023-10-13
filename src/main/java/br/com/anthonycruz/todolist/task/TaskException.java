package br.com.anthonycruz.todolist.task;

public class TaskException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public TaskException(String msg) {
    super(msg);
  }
}
