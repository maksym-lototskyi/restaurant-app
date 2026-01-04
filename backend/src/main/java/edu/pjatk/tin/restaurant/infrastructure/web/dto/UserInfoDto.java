package edu.pjatk.tin.restaurant.infrastructure.web.dto;

import java.util.List;
import java.util.UUID;

public record UserInfoDto(UUID id, String email, List<String> roles) {
}
