package main.services;

import main.models.Match;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class MatchService extends Service<Match> {
    public MatchService() {
        super(Match.class);
    }
}
