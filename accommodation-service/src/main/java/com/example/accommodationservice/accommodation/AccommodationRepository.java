package com.example.accommodationservice.accommodation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    boolean existsByName(String name);
}
