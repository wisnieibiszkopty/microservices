package com.example.accommodationservice.accommodation;

import com.example.accommodationservice.accommodation.dtos.AccommodationRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accommodations")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class AccommodationController {
    private final AccommodationService accommodationService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'OWNER', 'WORKER', 'ADMIN')")
    public ResponseEntity<Accommodation> get(@PathVariable Long id){
        return ResponseEntity.ok(accommodationService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'OWNER', 'WORKER', 'ADMIN')")
    public ResponseEntity<List<Accommodation>> getAll(){
        return ResponseEntity.ok(accommodationService.getAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER', 'OWNER', 'WORKER', 'ADMIN')")
    public ResponseEntity<List<Accommodation>> getAllByName(
            @RequestParam(name = "name") String name
            ){
        return ResponseEntity.ok(accommodationService.searchByName(name));
    }

    @GetMapping("/location/{city}")
    @PreAuthorize("hasAnyRole('USER', 'OWNER', 'WORKER', 'ADMIN')")
    public ResponseEntity<List<Accommodation>> getAllByLocation(@PathVariable String city){
        return ResponseEntity.ok(accommodationService.getByLocation(city));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    public ResponseEntity<Accommodation> create(@RequestBody AccommodationRequest accommodation){
        return ResponseEntity.ok(accommodationService.create(accommodation));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    public ResponseEntity<Accommodation> update(@PathVariable Long id, @RequestBody Accommodation accommodation){
        return ResponseEntity.ok(accommodationService.update(id, accommodation));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        accommodationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
