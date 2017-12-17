package main.controllers;

import main.models.Bet;
import main.models.Match;
import main.models.Team;
import main.models.User;
import main.services.BetService;
import main.services.MatchService;
import main.services.TeamService;
import main.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController extends Controller<Match> {
    @Autowired
    private MatchService matchService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private BetService betService;
    @Autowired
    private UserService userService;

    // Generation coefficients on match
    @Override
    public ResponseEntity createEntity(@RequestBody Match object) {
        try {
            if (object.getTeam1().getId().equals(object.getTeam2().getId()))
                return ResponseEntity.status(501).body("Two same teams can not play in one match");

            Team team1 = teamService.getById(object.getTeam1().getId());
            Team team2 = teamService.getById(object.getTeam2().getId());

            if (team1 == null)
                return ResponseEntity.status(501).body("Team 1 is not found");
            if (team2 == null)
                return ResponseEntity.status(501).body("Team 2 is not found");

            matchService.generateCoefficients(object, team1, team2);

            return super.createEntity(object);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Transactional
    @PostMapping("/{primaryKey}/results/generation")
    public ResponseEntity generateResult(@PathVariable Integer primaryKey) {
        try {
            Match match = matchService.getById(primaryKey);

            // Check, is finished the match?
            if (match.getResult() == null) {
                // Generate result of match
                String result = matchService.generateResult(match);

                matchService.updateResult(match, result);
                teamService.updateStatistic(match.getTeam1(), match.getTeam2(), result);

                // Get all not completed bets by this match id
                List<Bet> bets = betService.getNotCompletedBets(match);

                for (Bet bet : bets) {
                    // Check, is win this bet? And update profit/balance
                    Boolean isWin = bet.getResult().equals(result);
                    Integer profit = betService.complete(bet, match, result, isWin);
                    userService.updateBalance(bet, profit);
                }

                return ResponseEntity.status(200).build();
            } else {
                return ResponseEntity.status(404).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
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
