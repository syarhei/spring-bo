package main.controllers;

import main.models.Match;
import main.models.Team;
import main.services.MatchService;
import main.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matches")
public class MatchController extends Controller<Match> {
    @Autowired
    private MatchService matchService;

    @Autowired
    private TeamService teamService;

    @Override
    public ResponseEntity createEntity(@RequestBody Match object) {
        Team team1 = teamService.getById(object.getTeam1().getId());
        Team team2 = teamService.getById(object.getTeam2().getId());

        matchService.generateCoefficients(object, team1, team2);

        return super.createEntity(object);
    }

    @GetMapping("/{primaryKey}")
    public ResponseEntity getEntity(@PathVariable Integer primaryKey) {
        return super.getEntity(primaryKey);
    }

    @PutMapping("/{primaryKey}")
    public ResponseEntity updateEntity(@PathVariable Integer primaryKey, @RequestBody Match object) {
        object.setId(primaryKey);
        return super.updateEntity(primaryKey, object);
    }

    @DeleteMapping("/{primaryKey}")
    public ResponseEntity deleteEntity(@PathVariable Integer primaryKey) {
        return super.deleteEntity(primaryKey);
    }
}
