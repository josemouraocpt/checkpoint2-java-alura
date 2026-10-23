package br.com.alura.room_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.room_service.domain.room.RegisterRoomDto;
import br.com.alura.room_service.domain.room.RoomDto;
import br.com.alura.room_service.domain.room.RoomService;
import br.com.alura.room_service.domain.room.UpdateRoomDto;
import br.com.alura.room_service.infra.exception.ValidationException;
import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/v2/rooms")
public class RoomController {

    private final RoomService service;

    public RoomController(RoomService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> list(){
        List<RoomDto> salas = service.listAvailableRooms();
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> find(@PathVariable String id){
        try {
            RoomDto dto = service.findRoomById(id);
            return ResponseEntity.ok(dto);
        } catch (ValidationException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody @Valid RegisterRoomDto dto){
        try {
            service.registerRoom(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sala cadastrada com sucesso!");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody @Valid UpdateRoomDto dto){
        try {
            service.updateRoom(dto, id);
            return ResponseEntity.ok("Sala atualizada com sucesso!");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
