package com.example.accommodationservice.accommodation;

import com.example.accommodationservice.accommodation.dtos.AccommodationRequest;
import com.example.accommodationservice.accommodation.dtos.AccommodationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accommodations")
@RequiredArgsConstructor
@Slf4j
public class AccommodationController {
    private final AccommodationService accommodationService;

    @GetMapping("/{id}")
    public ResponseEntity<Accommodation> get(@PathVariable Long id){
        return ResponseEntity.ok(accommodationService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Accommodation>> getAll(
            @RequestParam(name = "search", required = false) String phrase,
            @RequestParam(name = "type", required = false)AccommodationType type
            ){
        return ResponseEntity.ok(accommodationService.getAll(phrase, type));
    }

    @PostMapping
    public ResponseEntity<Accommodation> create(@RequestBody AccommodationRequest accommodation){
        return ResponseEntity.ok(accommodationService.create(accommodation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Accommodation> update(@PathVariable Long id, @RequestBody Accommodation accommodation){
        return ResponseEntity.ok(accommodationService.update(id, accommodation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        accommodationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
