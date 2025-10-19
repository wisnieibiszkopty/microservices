package com.example.accommodationservice.room;

import com.example.accommodationservice.accommodation.Accommodation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "rooms")
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer number;

    @NotNull
    private Boolean occupied;

    @Min(1)
    @NotNull
    private Integer bedsCount;

    @Min(0)
    private Integer floor;

    @Min(1)
    private Integer guestsCount;

    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    private Double area;

    @Column(name = "room_features")
    @ElementCollection
    private List<String> features;

    @Column(name = "accommodation_id", insertable = false, updatable = false)
    private Long accommodationId;

    @ManyToOne
    @JoinColumn(name="accommodation_id")
    @JsonBackReference
    private Accommodation accommodation;
}
