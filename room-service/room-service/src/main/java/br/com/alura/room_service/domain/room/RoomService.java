package br.com.alura.room_service.domain.room;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.room_service.infra.exception.ValidationException;

@Service 
public class RoomService {

    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<RoomDto> listAvailableRooms(){
        List<RoomDto> rooms = repository.findByStatus(Status.ACTIVE).stream().map(RoomDto::new).toList();
        return rooms;
    }

    public RoomDto findRoomById(String id){
        Room room = loadRoom(id);
        return new RoomDto(room);
    }

    @Transactional
    public void registerRoom(RegisterRoomDto dto) {
        boolean alreadyCadastred = repository.existsByNameAndCapacityAndStatus(dto.name(), dto.capacity(), Status.ACTIVE);
        if(alreadyCadastred){
            throw new ValidationException("A sala informada já esta cadastrada e ativa!");
        }
        repository.save(new Room(dto));
    }

    @Transactional
    public void updateRoom(UpdateRoomDto dto, Long id) {
        Room room = repository.getReferenceById(id);
        room.updateData(dto);   
        repository.save(room);
    }


    private Room loadRoom(String id){
        Optional<Room> optional;
        try {
            Long idLong = Long.parseLong(id);
            optional = repository.findById(idLong);
        } catch (NumberFormatException e) {
            throw new ValidationException("O id fornecido não é válido!");
        }
        return optional.orElseThrow(() -> new ValidationException("Sala não encontrada!"));
    }
}
