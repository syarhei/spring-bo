package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import main.DAO.TeamDAO;
import main.model.Team;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamDAO teamDAO;

    @GetMapping
    public ResponseEntity getCustomers() {
        return ResponseEntity.ok(teamDAO.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getCustomer(@PathVariable Integer id) {

        Team team = teamDAO.getById(id);
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

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Integer id) {

        if (null == teamDAO.delete(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity updateCustomer(@PathVariable Integer id, @RequestBody Team team) {

        team.setId(id);
        Team result = teamDAO.update(id, team);

        if (null == result) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

}