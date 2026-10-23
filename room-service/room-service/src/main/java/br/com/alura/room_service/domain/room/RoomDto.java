package br.com.alura.room_service.domain.room;

public record RoomDto(
    Long id,
    String nome,
    int capacidade,
    Status status
) {
    public RoomDto(Room sala){
        this(sala.getId(), sala.getName(), sala.getCapacity(), sala.getStatus());
    }
}
