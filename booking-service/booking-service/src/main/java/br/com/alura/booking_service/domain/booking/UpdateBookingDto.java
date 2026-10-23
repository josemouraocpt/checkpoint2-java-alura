package br.com.alura.booking_service.domain.booking;

import java.time.LocalDateTime;

public record UpdateBookingDto(
    LocalDateTime startTime,
    LocalDateTime endTime
) {

}
