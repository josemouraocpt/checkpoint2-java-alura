package br.com.alura.booking_service.domain.booking;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.booking_service.infra.exception.ValidationException;
import br.com.alura.booking_service.infra.http.RoomClient;

@Service 
public class BookingService {

    private final BookingRepository repository;
    private final RoomClient room;

    public BookingService(BookingRepository repository, RoomClient room) {
        this.repository = repository;
        this.room = room;
    }

    public List<BookingDto> listActiveBookings() {
        List<BookingDto> bookings = repository.findByStatus(Status.ACTIVE).stream().map(BookingDto::new).toList();
        return bookings;
    }

    public BookingDto findBookingById(String id) {
        Booking booking = loadBooking(id);
        return new BookingDto(booking);
    }

    @Transactional
    public void createBooking(CreateBookingDto dto, String authorization) {
        boolean roomIsInactive = verifyRoom(dto.idRoom(), authorization);

        if(roomIsInactive){
            throw new ValidationException("A sala informada está Inativa!");
        }

        if(dto.startTime().isAfter(dto.endTime())){
            throw new ValidationException("O horario de início deve ser maior que o de término!");
        }

        boolean roomIsReserved = repository.existsByRoomAndStartTimeBeforeAndEndTimeAfter(dto.idRoom(), dto.endTime(), dto.startTime());
        if(roomIsReserved){
            throw new ValidationException("A sala informada já possui uma reserva no intervalo de tempo informado!");
        }

        repository.save(new Booking(dto));
    }

    @Transactional
    public void updateBooking(UpdateBookingDto dto, Long id) {
        if(dto.startTime().isAfter(dto.endTime())){
            throw new ValidationException("O horario de início deve ser maior que o de término!");
        }
        Booking booking = repository.getReferenceById(id);
        booking.updateData(dto);
        repository.save(booking);
    }

    @Transactional
    public void cancelBooking(String id) {
        Booking booking = loadBooking(id);
        booking.cancel();
        repository.save(booking);
    }

    private Booking loadBooking(String id){
        Optional<Booking> optional;
        try {
            Long idLong = Long.parseLong(id);
            optional = repository.findById(idLong);
        } catch (NumberFormatException e) {
            throw new ValidationException("O id fornecido não é válido!");
        }
        return optional.orElseThrow(() -> new ValidationException("Reserva não encontrada!"));
    }

    private boolean verifyRoom(Long id, String authorization){
        try {
            var response = room.findRoom(id, authorization);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.getBody());
            if(jsonNode.get("status").asText().equals("INACTIVE")){
                return true;
            }
            return false;
        } catch (JsonMappingException e) {
            throw new ValidationException("Impossivel mapear a resposta retornada!");
        }catch (JsonProcessingException e) {
            throw new ValidationException("Impossivel processar a resposta retornada!");
        }
    }
}
