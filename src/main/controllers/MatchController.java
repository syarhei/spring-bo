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
        Team team1 = teamService.getById(object.getTeam1().getId());
        Team team2 = teamService.getById(object.getTeam2().getId());

        matchService.generateCoefficients(object, team1, team2);

        return super.createEntity(object);
    }

    @PostMapping("/{primaryKey}/results/generation")
    public ResponseEntity generateResult(@PathVariable Integer primaryKey) {
        Match match = matchService.getById(primaryKey);

        if (match.getResult() == null) {
            // TODO: Create validate generation
            Double randomValue = Math.random();
            String result = randomValue < 0.33 ? "w1" : randomValue < 0.66 ? "w2" :"d";

            match.setResult(result);
            matchService.update(match.getId(), match);

            teamService.updates(match.getTeam1(), match.getTeam2(), result);

            List<Bet> bets = betService.getNotCompletedBets(match);

            for (Bet bet : bets) {
                bet.setCompleteness(true);
                if (bet.getResult().equals(result)) {
                    BigDecimal coefficient = result.equals("w1") ? match.getCoefficientWin1() :
                            result.equals("w2") ? match.getCoefficientWin2() :
                                    match.getCoefficientDraw();
                    Double profit = bet.getPrice() * coefficient.doubleValue() - bet.getPrice();
                    bet.setProfit(profit.intValue());
                    bet.setCompleteness(true);
                    betService.update(bet.getId(), bet);

                    User user = bet.getUser();
                    user.setBalance(user.getBalance() + profit.intValue());

                    userService.update(user.getNickname(), user);
                } else {
                    Integer profit = -bet.getPrice();
                    bet.setProfit(profit);
                    bet.setCompleteness(true);
                    betService.update(bet.getId(), bet);

                    User user = bet.getUser();
                    user.setBalance(user.getBalance() + profit);

                    userService.update(user.getNickname(), user);
                }
            }

        }

        return ResponseEntity.status(200).build();
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
