package com.example.accommodationservice.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("""
        SELECT r FROM Room r
        WHERE
            (:minPrice IS NULL OR r.price >= :minPrice)
            AND (:maxPrice IS NULL OR r.price <= :maxPrice)
            AND (:occupied IS NULL OR r.occupied = :occupied)
            AND (:minGuests IS NULL OR r.guestsCount >= :minGuests)
        """)
    List<Room> getAll(
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("occupied") Boolean occupied,
            @Param("minGuests") Integer minGuests
    );

}
