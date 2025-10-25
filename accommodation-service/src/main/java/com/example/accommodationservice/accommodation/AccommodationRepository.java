package com.example.accommodationservice.accommodation;

import com.example.accommodationservice.accommodation.dtos.AccommodationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    boolean existsByName(String name);
    List<Accommodation> findByNameContainingIgnoreCase(String name);
    List<Accommodation> findByCityContainingIgnoreCase(String location);
    List<Accommodation> findAllByType(AccommodationType type);

    @Query("""
    SELECT a FROM Accommodation a
    WHERE
        (:type IS NULL OR a.type = :type)
        AND (
            :phrase IS NULL
            OR LOWER(a.name) LIKE LOWER(CONCAT('%', :phrase, '%'))
            OR LOWER(a.city) LIKE LOWER(CONCAT('%', :phrase, '%'))
            OR LOWER(a.address) LIKE LOWER(CONCAT('%', :phrase, '%'))
        )
""")
    List<Accommodation> findByPhraseAndType(
            @Param("phrase") String phrase,
            @Param("type") AccommodationType type
    );
}
