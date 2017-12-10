package main.services;

import main.models.Team;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class TeamService extends Service<Team> {
    public TeamService() {
        super(Team.class);
    }

    // Update teams after generation result of match
    public void updateStatistic(Team team1, Team team2, String result) {
        team1.setGames(team1.getGames() + 1);
        team2.setGames(team2.getGames() + 1);

        if (result.equals("W1")) {
            team1.setWins(team1.getWins() + 1);
            team2.setLoses(team2.getLoses() + 1);
            team1.setPoints(team1.getPoints() + 3);
        }

        if (result.equals("W2")) {
            team1.setLoses(team1.getLoses() + 1);
            team2.setWins(team2.getWins() + 1);
            team2.setPoints(team2.getPoints() + 3);
        }

        if (result.equals("D")) {
            team1.setDraws(team1.getDraws() + 1);
            team2.setDraws(team2.getDraws() + 1);
            team1.setPoints(team1.getPoints() + 1);
            team2.setPoints(team2.getPoints() + 1);
        }

        super.update(team1.getId(), team1);
        super.update(team2.getId(), team2);
    }
}
