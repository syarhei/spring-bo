package main.DAO;

import main.model.Team;

import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class TeamDAO extends DAO<Team> {
    public TeamDAO() {
        super(Team.class);
    }
}