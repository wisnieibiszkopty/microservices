package com.example.accommodationservice.accommodation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "accommodations")
@Data
public class Accommodation {
    @Id
    private Long id;
}
