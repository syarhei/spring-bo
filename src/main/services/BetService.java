package main.services;

import main.models.Bet;
import main.models.Match;
import main.models.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Repository
@Transactional
public class BetService extends Service<Bet> {
    public BetService() {
        super(Bet.class);
    }

    public List getBets(User user) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Bet> query = builder.createQuery(Bet.class);

        Root<Bet> root = query.from(Bet.class);

        query.select( root )
                .where(builder.equal(root.get("user"), user));

        return session.createQuery(query).getResultList();
    }

    public List getNotCompletedBets(Match match) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Bet> query = builder.createQuery(Bet.class);

        Root<Bet> root = query.from(Bet.class);

        query.select( root )
                .where(builder.equal(root.get("completeness"), false))
                .where(builder.equal(root.get("match"), match));

        return session.createQuery(query).getResultList();
    }

    public Integer complete(Bet bet, Match match, String result, boolean isWin) {
        bet.setCompleteness(true);
        if (isWin) {
            BigDecimal coefficient = result.equals("W1") ? match.getCoefficientWin1() :
                    result.equals("W2") ? match.getCoefficientWin2() :
                            match.getCoefficientDraw();
            Double profit = bet.getPrice() * coefficient.doubleValue() - bet.getPrice();
            bet.setProfit(profit.intValue());
            super.update(bet.getId(), bet);
            return profit.intValue();
        } else {
            Integer profit = -bet.getPrice();
            bet.setProfit(profit);
            bet.setCompleteness(true);
            super.update(bet.getId(), bet);
            return profit;
        }
    }
}
