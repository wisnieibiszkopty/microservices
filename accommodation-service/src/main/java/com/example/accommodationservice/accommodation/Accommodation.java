package com.example.accommodationservice.accommodation;

import com.example.accommodationservice.accommodation.dtos.AccommodationRequest;
import com.example.accommodationservice.accommodation.dtos.AccommodationType;
import com.example.accommodationservice.room.Room;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Indexed;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accommodations")
@Data
@Indexed
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    private String description;

    @NotBlank
    private String city;

    @NotBlank
    private String address;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccommodationType type;

    @Column(name = "accommodation_features")
    @ElementCollection
    private List<String> features;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
        room.setAccommodation(this);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
        room.setAccommodation(null);
    }

    public static Accommodation fromRequest(AccommodationRequest request){
        var accommodation = new Accommodation();
        accommodation.setName(request.name());
        accommodation.setDescription(request.description());
        accommodation.setAddress(request.address());
        accommodation.setCity((request.city()));
        accommodation.setFeatures(request.features());
        return accommodation;
    }

}
