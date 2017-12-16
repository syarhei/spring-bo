package main.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import main.models.User;
import main.services.UserService;
import main.utils.Middleware;
import main.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity login(@RequestBody User object, HttpServletResponse response) {
        try {
            boolean check = userService.checkCredentials(object);
            if (check) {
                String token = Token.stringify(object);
                response.addCookie(new Cookie("token", token));
            }
            return ResponseEntity.status(check  ? 200 : 403 ).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity getCredentials(HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies();
            Cookie cookie = Token.getCookie(cookies, "token");

            if (cookie == null)
                return ResponseEntity.status(404).build();

            Jws<Claims> token = Token.parse(cookie.getValue());
            return ResponseEntity.status(200).body(token.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity logout(HttpServletResponse response) {
        try {
            Cookie token = new Cookie("token", null);
            token.setMaxAge(0);
            response.addCookie(token);
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
