package main.services;

import main.models.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserService extends Service<User> {
    public UserService() {
        super(User.class);
    }

    public boolean checkCredentials(User object) {
        User user = super.getById(object.getNickname());
        if (user == null) {
            return false;
        }
        object.setRole(user.getRole());
        return user.getPassword().equals(object.getPassword());
    }
}
