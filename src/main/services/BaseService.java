package main.services;

import main.DAO.DAO;

import java.io.Serializable;

public abstract class BaseService<T> extends DAO<T> {
    public BaseService(Class template) {
        super(template);
    }

    public T update(Serializable id, T object) {
        T objectFromGet = getById(id);
        return objectFromGet == null ? null : update(object);
    }

    public T delete(Serializable id) {
        T objectFromGet = getById(id);
        if (objectFromGet == null) {
            return null;
        }
        else {
            return delete(objectFromGet);
        }
    }

    // TODO: Base business logic
}
