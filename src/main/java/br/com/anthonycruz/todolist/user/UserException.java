package br.com.anthonycruz.todolist.user;

public class UserException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public UserException(String msg) {
    super(msg);
  }
}
