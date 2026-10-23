package br.com.alura.booking_service.domain.booking;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record CreateBookingDto(
    @NotNull
    LocalDateTime startTime,
    @NotNull
    LocalDateTime endTime,
    @NotNull
    Long idRoom,
    @NotNull
    Long idUser
) {

}
