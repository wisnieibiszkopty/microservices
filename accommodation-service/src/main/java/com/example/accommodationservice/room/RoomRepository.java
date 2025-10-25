package com.example.accommodationservice.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.price <= :maxPrice AND r.price > :minPrice")
    List<Room> findByPriceRange(
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice);

    @Query("""
        SELECT r FROM Room r
        WHERE r.occupied = false
          AND r.guestsCount >= :minGuestsCount
          AND r.accommodation.id = :accommodationId
        ORDER BY r.guestsCount ASC
    """)
    List<Room> findAvailableRooms(
            @Param("minGuestsCount") Integer minGuestsCount,
            @Param("accommodationId") Long accommodationId
    );

}
