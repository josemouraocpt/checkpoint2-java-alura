package br.com.alura.room_service.domain.room;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByStatus(Status active);

    boolean existsByNameAndCapacityAndStatus(String name, int capacidade, Status active);

    boolean existsByIdAndStatus(Long idRoom, Status inactive);
} 