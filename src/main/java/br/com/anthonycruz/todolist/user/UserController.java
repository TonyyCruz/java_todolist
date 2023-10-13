package br.com.anthonycruz.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private IUserRepository userRepository;

  @PostMapping("/")
  public ResponseEntity<UserModel> create(@RequestBody UserModel userModel) {
    String username = userModel.getUsername();
    UserModel user = this.userRepository.findByUsername(username);

    if (user != null) {
      throw new UserException(
          "O username '" + username + "' já existe.");
    }

    String hashedPassword = BCrypt.withDefaults().hashToString(
        12, userModel.getPassword().toCharArray());
    userModel.setPassword(hashedPassword);

    UserModel userCreated = this.userRepository.save(userModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
  }
}
