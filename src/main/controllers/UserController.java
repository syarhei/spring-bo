package main.controllers;

import main.models.User;
import main.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController extends Controller<User> {
    @Autowired
    private UserService userService;

    // Registration 'POST: /api/users'
    @Override
    public ResponseEntity createEntity(User object) {
        return super.createEntity(object);
    }

    @GetMapping("/{primaryKey}")
    public ResponseEntity getEntity(@PathVariable String primaryKey) {
        return super.getEntity(primaryKey);
    }

    @PutMapping("/{primaryKey}")
    public ResponseEntity updateEntity(@PathVariable String primaryKey, @RequestBody User object) {
        object.setNickname(primaryKey);
        return super.updateEntity(primaryKey, object);
    }

    @DeleteMapping("/{primaryKey}")
    public ResponseEntity deleteEntity(@PathVariable String primaryKey) {
        return super.deleteEntity(primaryKey);
    }
}