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
    public ResponseEntity<List<Room>> getAll(
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(name = "occupied", required = false) Boolean occupied,
            @RequestParam(name = "guestsCount", required = false) Integer guestsCount
            ){
        return ResponseEntity.ok(roomService.getAll(minPrice, maxPrice, occupied, guestsCount));
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
