package com.alex.user_service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(@NotBlank @Size(min = 3, max = 20) String username, @NotBlank @Size(min = 6, max = 32) String password) {
    
}
