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

    @GetMapping("/{id}")
    public ResponseEntity getCustomer(@PathVariable("id") Long id) {

        Team customer = teamDAO.get(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody Team customer) {

        teamDAO.create(customer);

        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Long id) {

        if (null == teamDAO.delete(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity updateCustomer(@PathVariable Long id, @RequestBody Team customer) {

        customer = teamDAO.update(id, customer);

        if (null == customer) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer);
    }

}