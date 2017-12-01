package main.services;

import main.models.User;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class UserService extends BaseService<User> {
    public UserService() {
        super(User.class);
    }
}