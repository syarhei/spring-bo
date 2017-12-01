package main.services;

import main.models.Team;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.io.Serializable;

@Repository
@Transactional
public class TeamService extends BaseService<Team> {
    public TeamService() {
        super(Team.class);
    }
}
