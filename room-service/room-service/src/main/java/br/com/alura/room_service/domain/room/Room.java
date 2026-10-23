package br.com.alura.room_service.domain.room;

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
@Table(name = "rooms")
@Getter 
@Setter 
@NoArgsConstructor 
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int capacity;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Room(RegisterRoomDto dto) {
        this.name = dto.name();
        this.capacity = dto.capacity();
        this.status = Status.ACTIVE;
    }

    public void updateData(UpdateRoomDto dto) {
        this.name = dto.name();
        this.capacity = dto.capacity();
        this.status = dto.status();
    }
}
