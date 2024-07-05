package com.unir.users.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AddCommentRequest {
	private Long hotelId;
	private String comment;
}
