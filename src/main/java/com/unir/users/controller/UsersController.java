package com.unir.users.controller;

import com.unir.users.model.db.User;
import com.unir.users.model.request.CreateUserRequest;
import com.unir.users.model.request.LoginRequest;
import com.unir.users.model.request.UpdateFavoritesRequest;
import com.unir.users.model.request.AddCommentRequest;
import com.unir.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UsersService service;

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody CreateUserRequest request) {
        User createdUser = service.createUser(request);
        if (createdUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(
        @RequestParam(required = false) String username,
        @RequestParam(required = false) String password,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String favorites,
        @RequestParam(required = false) String comments
    ) {
        List<User> users = service.getUsers(username, password, email, favorites, comments);
        if (users != null) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user = service.getUser(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        Boolean removed = service.removeUser(userId);
        if (Boolean.TRUE.equals(removed)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users/session")
    public ResponseEntity<User> createSession(@RequestBody LoginRequest request) {
        boolean isAuthenticated = service.authenticateUser(request.getUsername(), request.getPassword());
        if (isAuthenticated) {
            User user = service.findUserByUsername(request.getUsername());
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    @PutMapping("/users/{userId}/favorites")
    public ResponseEntity<Void> updateFavorites(@PathVariable String userId, @RequestBody UpdateFavoritesRequest request) {
        boolean updated = service.updateFavorites(userId, request.getHotelIds());
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/users/{userId}/favorites/{hotelId}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable String userId, @PathVariable String  hotelId) {
        boolean updated = service.deleteFavorite(userId, hotelId);
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/users/{userId}/favorites")
    public ResponseEntity<String> getFavorites(@PathVariable String userId) {
        User user = service.getUser(userId);
        if (user != null) {
            return ResponseEntity.ok(user.getFavorites());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users/{userId}/comments")
    public ResponseEntity<Void> addComment(@PathVariable String userId, @RequestBody AddCommentRequest request) {
        log.info("Received request to add comment. User ID: {}, Hotel ID: {}, Comment: {}", userId, request.getHotelId(), request.getComment());
        boolean added = service.addComment(userId, request.getHotelId(), request.getComment());
        if (added) {
            log.info("Comment added successfully for User ID: {}", userId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            log.warn("Failed to add comment for User ID: {}", userId);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/hotels/{hotelId}/comments")
    public ResponseEntity<List<String>> getCommentsByHotel(@PathVariable String hotelId) {
        List<String> comments = service.getCommentsByHotel(hotelId);
        if (comments != null) {
            return ResponseEntity.ok(comments);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }
}
