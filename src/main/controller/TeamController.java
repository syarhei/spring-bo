package main.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import main.service.TeamDAO;
import main.model.Team;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamDAO teamDAO;

    @GetMapping
    public ResponseEntity getCustomers() {
        return ResponseEntity.ok(teamDAO.list());
    }

    @GetMapping("/{name}")
    public ResponseEntity getCustomer(@PathVariable String name) {

        Team team = teamDAO.get(name);
        if (team == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(team);
    }

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody Team team) {

        Team result = teamDAO.create(team);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity deleteCustomer(@PathVariable String name) {

        if (null == teamDAO.delete(name)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{name}")
    public ResponseEntity updateCustomer(@PathVariable String name, @RequestBody Team team) {

        team.setName(name);
        Team result = teamDAO.update(team);

        if (null == result) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

}