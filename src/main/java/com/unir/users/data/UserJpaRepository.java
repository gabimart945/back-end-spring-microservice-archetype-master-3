package com.unir.users.data;

import com.unir.users.data.utils.SearchCriteria;
import com.unir.users.model.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
        User findByUsername(String username);
        List<User> findByfavoritesContains(Long hotelId);
        List<User> findByCommentsContains(String comment);

//        List<User> findAll(SearchCriteria<User> spec);
}
