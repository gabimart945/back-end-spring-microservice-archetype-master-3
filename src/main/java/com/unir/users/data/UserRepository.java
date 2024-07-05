package com.unir.users.data;

import com.unir.users.data.utils.SearchCriteria;
import com.unir.users.data.utils.SearchOperation;
import com.unir.users.data.utils.SearchStatement;
import com.unir.users.model.db.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserJpaRepository repository;

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username);
    }
    public List<User> getUsersByFavoriteHotel(Long hotelId) {
        return repository.findByfavoritesContains(hotelId);
    }

    public List<User> getUsersByComment(String comment) {
        return repository.findByCommentsContains(comment);
    }

    public List<User> search(String username, String password, String email, String favorites, String comments) {
        SearchCriteria<User> spec = new SearchCriteria<>();
        if (StringUtils.isNotBlank(username)) {
            spec.add(new SearchStatement("username", username, SearchOperation.MATCH));
        }
        if (StringUtils.isNotBlank(password)) {
            spec.add(new SearchStatement("password", password, SearchOperation.MATCH));
        }
        if (StringUtils.isNotBlank(email)) {
            spec.add(new SearchStatement("email", email, SearchOperation.MATCH));
        }
        if (StringUtils.isNotBlank(favorites)) {
            spec.add(new SearchStatement("favorites", favorites, SearchOperation.MATCH));
        }
        if (StringUtils.isNotBlank(comments)) {
            spec.add(new SearchStatement("comments", comments, SearchOperation.MATCH));
        }
        return repository.findAll();

    }

    public List<User> findAll() {
        return List.of();
    }

    public Optional<User> findById(long l) {
        return Optional.empty();
    }


}
