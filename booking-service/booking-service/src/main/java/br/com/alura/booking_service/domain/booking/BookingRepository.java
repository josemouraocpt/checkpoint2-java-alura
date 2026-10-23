package br.com.alura.booking_service.domain.booking;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStatus(Status active);

    boolean existsByRoomAndStartTimeBeforeAndEndTimeAfter(Long room, LocalDateTime endTime, LocalDateTime startTime);
}
