package com.unir.users.service;

import com.unir.users.model.db.User;
import com.unir.users.model.request.CreateUserRequest;

import java.util.List;

public interface UsersService {

	List<User> getUsers(String username, String password, String email, String favorites, String comments);

	User getUser(String userId);

	User createUser(CreateUserRequest request);

	boolean removeUser(String userId);

	boolean authenticateUser(String username, String password);

	User findUserByUsername(String username);

	boolean updateFavorites(String userId, List<Long> hotelIds);

	boolean deleteFavorite(String userId, String hotelId);

	boolean addComment(String userId, Long hotelId, String comment);

	List<String> getCommentsByHotel(String hotelId);
}
