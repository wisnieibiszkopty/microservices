package com.example.accommodationservice.room;

import com.example.accommodationservice.room.dtos.CreateRoomRequest;
import com.example.accommodationservice.room.dtos.UpdateRoomRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/{id}")
    public ResponseEntity<Room> getById(@PathVariable Long id){
        return ResponseEntity.ok(roomService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAll(){
        return ResponseEntity.ok(roomService.getAll());
    }

    @GetMapping("/price")
    public ResponseEntity<List<Room>> getAllByPriceRange(
            @RequestParam(name = "minPrice") BigDecimal minPrice,
            @RequestParam(name = "maxPrice") BigDecimal maxPrice
    ){
        return ResponseEntity.ok(roomService.getAllByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms(
            @RequestParam(name = "accommodationId") Long accommodationId,
            @RequestParam(name = "minGuestsCount") Integer minGuestsCount
    ){
        return ResponseEntity.ok(roomService.getAllByGuestCountAndAccommodationId(accommodationId, minGuestsCount));
    }

    @PostMapping
    public ResponseEntity<Room> create(@RequestBody CreateRoomRequest room){
        return ResponseEntity.ok(roomService.create(room));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Room> updateOccupation(
            @PathVariable Long id,
            @RequestParam(name = "occupied") Boolean occupied){
        return ResponseEntity.ok(roomService.changeOccupation(id, occupied));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(
            @PathVariable Long id,
            @RequestBody UpdateRoomRequest room
            ){
        return ResponseEntity.ok(roomService.update(room, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Room> delete(@PathVariable Long id){
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
