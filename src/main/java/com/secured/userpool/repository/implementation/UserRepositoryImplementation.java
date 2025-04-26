package com.secured.userpool.repository.implementation;

import com.secured.userpool.model.dao.Password;
import com.secured.userpool.model.dao.User;
import com.secured.userpool.repository.UserRepository;
import com.secured.userpool.repository.implementation.provider.PasswordRepositoryJPA;
import com.secured.userpool.repository.implementation.provider.UserRepositoryJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImplementation implements UserRepository {

    private final UserRepositoryJPA userRepositoryJPA;
    private final PasswordRepositoryJPA passwordRepositoryJPA;

    public Optional<User> findByUsername(String username) {
        return userRepositoryJPA.findByUsername(username);
    }

    public User save(User user) {
        return userRepositoryJPA.save(user);
    }

    public Password savePassword(Password password) {
        return passwordRepositoryJPA.save(password);
    }


    public List<User> get(String sort, String order) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        if (sort.equalsIgnoreCase("id")) {
            sort = "id";
        }

        return userRepositoryJPA.findAll(Sort.by(direction, sort));
    }

}
