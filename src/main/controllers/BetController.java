package main.controllers;

import main.models.Bet;
import main.services.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bets")
public class BetController extends Controller<Bet> {
    @Autowired
    private BetService betService;

    @GetMapping("/{primaryKey}")
    public ResponseEntity getEntity(@PathVariable Integer primaryKey) {
        return super.getEntity(primaryKey);
    }

    @PutMapping("/{primaryKey}")
    public ResponseEntity updateEntity(@PathVariable Integer primaryKey, @RequestBody Bet object) {
        object.setId(primaryKey);
        return super.updateEntity(primaryKey, object);
    }

    @DeleteMapping("/{primaryKey}")
    public ResponseEntity deleteEntity(@PathVariable Integer primaryKey) {
        return super.deleteEntity(primaryKey);
    }
}
