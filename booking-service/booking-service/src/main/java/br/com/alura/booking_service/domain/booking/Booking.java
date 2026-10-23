package br.com.alura.booking_service.domain.booking;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "bookings")
@Getter 
@Setter 
@NoArgsConstructor 
public class Booking {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long user;
    private Long room;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Booking(CreateBookingDto dto) {
        this.startTime = dto.startTime();
        this.endTime = dto.endTime();
        this.room = dto.idRoom();
        this.user = dto.idUser();
        this.status = Status.ACTIVE;
    }

    public void updateData(UpdateBookingDto dto){
        this.startTime = dto.startTime();
        this.endTime = dto.endTime();
    }

    public void cancel(){
        this.status = Status.CANCELLED;
    }
}
