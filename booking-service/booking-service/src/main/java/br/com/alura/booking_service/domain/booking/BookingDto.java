package br.com.alura.booking_service.domain.booking;

import java.time.LocalDateTime;

public record BookingDto(
    Long id,
    LocalDateTime startTime,
    LocalDateTime endTime,
    Status status,
    Long room_id,
    Long user_id
) {
    public BookingDto(Booking booking){
        this(booking.getId(), booking.getStartTime(), booking.getEndTime(), booking.getStatus(), booking.getRoom(), booking.getUser());
    }
}
