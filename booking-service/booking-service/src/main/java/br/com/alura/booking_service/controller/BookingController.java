package br.com.alura.booking_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.booking_service.domain.booking.BookingDto;
import br.com.alura.booking_service.domain.booking.BookingService;
import br.com.alura.booking_service.domain.booking.CreateBookingDto;
import br.com.alura.booking_service.domain.booking.UpdateBookingDto;
import br.com.alura.booking_service.infra.exception.ValidationException;
import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/v2/bookings")
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> list(){
        List<BookingDto> reservas = service.listActiveBookings();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> find(@PathVariable String id){
        try {
            BookingDto dto = service.findBookingById(id);
            return ResponseEntity.ok(dto);
        } catch (ValidationException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/cancel")
    public ResponseEntity<String> cancel(@PathVariable String id){
        try {
            service.cancelBooking(id);
            return ResponseEntity.ok("Reserva cancelada do sucesso!");
        } catch (ValidationException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody @Valid CreateBookingDto dto){
        try {
            service.createBooking(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reserva cadastrada com sucesso!");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody @Valid UpdateBookingDto dto){
        try {
            service.updateBooking(dto, id);
            return ResponseEntity.ok("Reserva atualizada com sucesso!");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
