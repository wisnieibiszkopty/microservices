package com.example.accommodationservice.room.dtos;

import java.math.BigDecimal;

public record RoomsFilter(
   Boolean occupied,
   BigDecimal priceFrom,
   BigDecimal priceTo,
   Integer guestsCount
) {}
