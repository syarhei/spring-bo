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
}
