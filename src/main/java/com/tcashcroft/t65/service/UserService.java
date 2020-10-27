package com.tcashcroft.t65.service;

import com.tcashcroft.t65.db.mysql.UsersDao;
import com.tcashcroft.t65.exception.UserExistsException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Setter
public class UserService {

    @Autowired
    private UsersDao usersDao;

    public String createUser(String username) throws UserExistsException {
        Optional<String> optionalUsername = usersDao.readUser(username);
        if (optionalUsername.isPresent()) {
            throw new UserExistsException();
        } else {
            usersDao.createUser(username);
            return username;
        }
    }

    public String getUser(String username) {
        Optional<String> optionalUsername = usersDao.readUser(username);
        if (optionalUsername.isPresent()) {
            return optionalUsername.get();
        } else {
            return null;
        }
    }
}
