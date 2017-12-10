package main.services;

import main.models.Bet;
import main.models.Match;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class BetService extends Service<Bet> {
    public BetService() {
        super(Bet.class);
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
}
