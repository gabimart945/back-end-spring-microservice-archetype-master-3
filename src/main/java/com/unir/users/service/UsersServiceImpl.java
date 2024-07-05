package com.unir.users.service;

import com.unir.users.data.UserRepository;
import com.unir.users.model.db.User;
import com.unir.users.model.request.CreateUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UserRepository repository;


	@Override
	public List<User> getUsers(String username, String password, String email, String favorites, String comments) {
		if (StringUtils.hasLength(username)|| StringUtils.hasLength(password)
				|| StringUtils.hasLength(email) || StringUtils.hasLength(favorites)
				|| StringUtils.hasLength(comments)) {
			return repository.search(username, password, email, favorites, comments);
		}
		List<User> users = repository.search(username, password, email, favorites, comments);
		return users.isEmpty() ? null : users;
    }


	@Override
	public User getUser(String userId) {
		Optional<User> user = Optional.ofNullable(repository.getById(Long.parseLong(userId)));
		return user.orElse(null);
	}

	@Override
	public User createUser(CreateUserRequest request) {
		User user = User.builder()
				.username(request.getUsername())
				.password(request.getPassword())
				.email(request.getEmail())
				.favorites("") // Inicializamos como cadena vacía
				.comments("") // Inicializamos como cadena vacía
				.build();
		return repository.save(user);
	}

	@Override
	public boolean removeUser(String userId) {
		Optional<User> user = Optional.ofNullable(repository.getById(Long.parseLong(userId)));
		if (user.isPresent()) {
			repository.delete(user.get());
			return true;
		}
		return false;
	}

	@Override
	public boolean authenticateUser(String username, String password) {
		User user = repository.getByUsername(username);
		return user != null && user.getPassword().equals(password);
	}

	public User findUserByUsername(String username) {
		return repository.getByUsername(username);
	}

	@Override
	public boolean updateFavorites(String userId, List<Long> hotelIds) {
		Optional<User> userOpt = Optional.ofNullable(repository.getById(Long.parseLong(userId)));
		if (userOpt.isPresent()) {
			User user = userOpt.get();

			// Convertir la lista de favoritos actual a un Set para evitar duplicados
			Set<Long> favoritesSet = new HashSet<>();
			if (user.getFavorites() != null && !user.getFavorites().isEmpty()) {
				String[] favoritesArray = user.getFavorites().split(",");
				for (String fav : favoritesArray) {
					favoritesSet.add(Long.parseLong(fav));
				}
			}

			// Agregar los nuevos favoritos al Set
			favoritesSet.addAll(hotelIds);

			// Convertir el Set de vuelta a una cadena
			String updatedFavorites = favoritesSet.stream()
					.map(String::valueOf)
					.collect(Collectors.joining(","));

			user.setFavorites(updatedFavorites);
			repository.save(user);
			return true;
		}
		return false;
	}


	@Override
	public boolean deleteFavorite(String userId, String hotelId) {
		Optional<User> userOpt = Optional.ofNullable(repository.getById(Long.parseLong(userId)));
		if (userOpt.isPresent()) {
			User user = userOpt.get();

			// Convertir la lista de favoritos actual a un Set para evitar duplicados
			Set<Long> favoritesSet = new HashSet<>();
			if (user.getFavorites() != null && !user.getFavorites().isEmpty()) {
				String[] favoritesArray = user.getFavorites().split(",");
				for (String fav : favoritesArray) {
					favoritesSet.add(Long.parseLong(fav));
				}
			}

			// Eliminar el favorito
			favoritesSet.remove(Long.parseLong(hotelId));

			// Convertir el Set de vuelta a una cadena
			String updatedFavorites = favoritesSet.stream()
					.map(String::valueOf)
					.collect(Collectors.joining(","));

			user.setFavorites(updatedFavorites);
			repository.save(user);
			return true;
		}
		return false;
	}


	@Override
	public boolean addComment(String userId, Long hotelId, String comment) {
		log.info("Adding comment. User ID: {}, Hotel ID: {}, Comment: {}", userId, hotelId, comment);

		Optional<User> user = Optional.ofNullable(repository.getById(Long.parseLong(userId)));
		if (user.isPresent()) {
			User userObj = user.get();
			String comments = userObj.getComments();
			String newComment = hotelId + ": " + comment;
			comments += (comments.isEmpty() ? "" : ", ") + newComment;
			userObj.setComments(comments);
			repository.save(userObj);
			return true;
		}
        return false;
    }


	@Override
	public List<String> getCommentsByHotel(String hotelId) {
		// Obtener todos los usuarios
		List<User> users = repository.getUsersByComment(hotelId);
		// Lista para almacenar todos los comentarios del hotel específico
		List<String> comments = new ArrayList<>();
		// Iterar sobre cada usuario y buscar comentarios relacionados con el hotel
		for (User user : users) {
			log.info("User: {}", user);
			String userComments = user.getComments();
			log.info("User comments: {}", userComments);
			if (userComments != null) {
				comments.addAll(Arrays.stream(userComments.split(", "))
						.filter(c -> c.startsWith(hotelId + ":"))
						.map(c -> user.getUsername() + ":" + c.substring(c.indexOf(":") + 1)) // Agregar nombre de usuario al comentario
						.collect(Collectors.toList()));
			}
		}
		return comments;
	}



}
