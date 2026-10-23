package br.com.alura.room_service.domain.room;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RegisterRoomDto(
    @NotBlank
    String name,
    @Min(1)
    int capacity
) {

}
