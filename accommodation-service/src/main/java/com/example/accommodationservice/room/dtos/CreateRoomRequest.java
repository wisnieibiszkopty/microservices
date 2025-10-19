package com.example.accommodationservice.room.dtos;

import java.math.BigDecimal;
import java.util.List;

public record CreateRoomRequest(
        Integer number,
        Boolean occupied,
        Integer floor,
        Integer guestsCount,
        Integer bedsCount,
        BigDecimal price,
        Double area,
        List<String> features,
        Long accommodationId
) {}
