package com.unir.users.model.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateFavoritesRequest {
	private List<Long> hotelIds;
}
