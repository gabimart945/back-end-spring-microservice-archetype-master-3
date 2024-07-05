package com.unir.users.model.request;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateUserRequest {
	private String username;
	private String password;
	private String email;
}

