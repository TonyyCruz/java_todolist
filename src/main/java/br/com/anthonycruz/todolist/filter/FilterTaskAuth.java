package br.com.anthonycruz.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Result;
import br.com.anthonycruz.todolist.user.IUserRepository;
import br.com.anthonycruz.todolist.user.UserModel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
  @Autowired
  private IUserRepository iUserRepository;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    // Pegaos dados do usuario (user e password)
    String authorization = request.getHeader("Authorization");
    authorization = authorization.substring("Basic".length()).trim();

    byte[] authDecoded = Base64.getDecoder().decode(authorization);

    String stringDecode = new String(authDecoded);
    String[] credentials = stringDecode.split(":");
    String username = credentials[0];
    String password = credentials[1];

    // Valida o user
    UserModel user = this.iUserRepository.findByUsername(username);

    if (user == null) {
      response.sendError(401);

    } else {
      // valida o password
      Result passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

      if (passwordVerify.verified) {
        filterChain.doFilter(request, response);

      } else {
        response.sendError(401);
      }

    }

  }

}
