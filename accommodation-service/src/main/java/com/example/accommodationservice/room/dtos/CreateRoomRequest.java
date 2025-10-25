package com.example.accommodationservice.room.dtos;

import java.math.BigDecimal;

public record CreateRoomRequest(
        Integer number,
        Boolean occupied,
        Integer floor,
        Integer guestsCount,
        Integer bedsCount,
        BigDecimal price,
        Double area,
        Long accommodationId
) {}
