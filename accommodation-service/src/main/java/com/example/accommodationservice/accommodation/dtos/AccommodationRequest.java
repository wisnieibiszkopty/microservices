package com.example.accommodationservice.accommodation.dtos;

import java.util.List;

public record AccommodationRequest(
        String name,
        String description,
        String city,
        String address,
        AccommodationType type,
        List<String> features
) {}