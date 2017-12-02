package main.services;

import main.models.Bet;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class BetService extends Service<Bet> {
    public BetService() {
        super(Bet.class);
    }
}
