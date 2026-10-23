package br.com.alura.booking_service.infra.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("room-service")
public interface RoomClient { 

    @RequestMapping(method = RequestMethod.GET, value = "/api/v2/rooms/{id}")
    ResponseEntity<String> findRoom(@PathVariable Long id, @RequestHeader("Authorization") String authorization);
}