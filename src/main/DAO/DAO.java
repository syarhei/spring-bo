package main.DAO;

import main.model.Team;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class DAO<T> {
    @Autowired
    private SessionFactory sessionFactory;

    public DAO(Class template) {
        this.template = template;
    }

    private Class template;

    public List getAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(template);
        return criteria.list();
    }

    public T getById(Serializable primaryKey) {
        Session session = sessionFactory.getCurrentSession();
        return (T)session.byId(template).load(primaryKey);
    }

    public T create(T object) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(object);
        return object;
    }

    public Serializable delete(Serializable primaryKey) {
        Session session = sessionFactory.getCurrentSession();
        T item = (T)session.byId(template).load(primaryKey);
        if (item == null) {
            return null;
        } else {
            session.delete(item);
            return primaryKey;
        }
    }

    public T update(Serializable primaryKey, T object) {
        Session session = sessionFactory.getCurrentSession();
        T item = (T)session.byId(template).load(primaryKey);
        if (item == null) {
            return null;
        } else {
            // TODO: Fix update
            Session sessionForUpdate = sessionFactory.openSession();
            sessionForUpdate.update(object);
            sessionForUpdate.close();
            return object;
        }
    }
}