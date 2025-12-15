package com.example.accommodationservice.room;

import com.example.accommodationservice.room.dtos.CreateRoomRequest;
import com.example.accommodationservice.room.dtos.UpdateRoomRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('WORKER') or hasRole('ADMIN')")
    public ResponseEntity<Room> getById(@PathVariable Long id){
        return ResponseEntity.ok(roomService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('WORKER') or hasRole('ADMIN')")
    public ResponseEntity<List<Room>> getAll(){
        return ResponseEntity.ok(roomService.getAll());
    }

    @GetMapping("/price")
    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('WORKER') or hasRole('ADMIN')")
    public ResponseEntity<List<Room>> getAllByPriceRange(
            @RequestParam(name = "minPrice") BigDecimal minPrice,
            @RequestParam(name = "maxPrice") BigDecimal maxPrice
    ){
        return ResponseEntity.ok(roomService.getAllByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/available")
    @PreAuthorize("hasRole('USER') or hasRole('OWNER') or hasRole('WORKER') or hasRole('ADMIN')")
    public ResponseEntity<List<Room>> getAvailableRooms(
            @RequestParam(name = "accommodationId") Long accommodationId,
            @RequestParam(name = "minGuestsCount") Integer minGuestsCount
    ){
        return ResponseEntity.ok(roomService.getAllByGuestCountAndAccommodationId(accommodationId, minGuestsCount));
    }

    @PostMapping
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<Room> create(@RequestBody CreateRoomRequest room){
        return ResponseEntity.ok(roomService.create(room));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('WORKER') or hasRole('ADMIN')")
    public ResponseEntity<Room> updateOccupation(
            @PathVariable Long id,
            @RequestParam(name = "occupied") Boolean occupied){
        return ResponseEntity.ok(roomService.changeOccupation(id, occupied));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('WORKER') or hasRole('ADMIN')")
    public ResponseEntity<Room> updateRoom(
            @PathVariable Long id,
            @RequestBody UpdateRoomRequest room
            ){
        return ResponseEntity.ok(roomService.update(room, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('WORKER') or hasRole('ADMIN')")
    public ResponseEntity<Room> delete(@PathVariable Long id){
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
