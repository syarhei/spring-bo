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
public class MatchController extends BaseController<Match> {
    @Autowired
    private MatchService matchService;

    @Autowired
    private TeamService teamService;

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

    @Override
    public ResponseEntity createEntity(@RequestBody Match object) {
//        Integer teamId1 = object.getTeam1().getId();
//        Team team1 = teamService.getById(teamId1);
//        Integer teamId2 = object.getTeam2().getId();
//        Team team2 = teamService.getById(teamId2);
//        object.setTeam1(team1);
//        object.setTeam2(team2);
        return super.createEntity(object);
    }
}
