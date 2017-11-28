package main.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import main.model.Team;

import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class TeamDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List list() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Team.class);
        return criteria.list();
    }

    public Team get(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return (Team)session.byId(Team.class).load(id);
    }

    public Team create(Team team) {
        Session session = sessionFactory.getCurrentSession();
        //session.createEntityGraph(Team.class);
        session.saveOrUpdate(team);
        return team;
    }

    public Long delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Team item = (Team) session.byId(Team.class).load(id);
        // check item == null => return 404
        if (item == null) {
            return null;
        } else {
            session.delete(item);
            return id;
        }
    }

    public Team update(Team team) {
        Session session = sessionFactory.getCurrentSession();
        Team item = (Team) session.byId(Team.class).load(team.getId());
        if (item == null) {
            return null;
        } else {
            Session sessionForUpdate = sessionFactory.openSession();
            sessionForUpdate.update(team);
            sessionForUpdate.close();
            return team;
        }
    }

}