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

    public boolean checkCredentials(String nickname, String password) {
        User user = super.getById(nickname);
        return !(user == null || !user.getPassword().equals(password));
    }
}
