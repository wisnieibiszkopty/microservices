package com.example.accommodationservice.accommodation;

import com.example.accommodationservice.accommodation.dtos.AccommodationRequest;
import com.example.accommodationservice.accommodation.dtos.AccommodationType;
import com.example.accommodationservice.room.Room;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accommodations")
@Data
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String description;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotNull(message = "Type cannot be null")
    @Enumerated(EnumType.STRING)
    private AccommodationType type;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
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
        accommodation.setType(request.type());
        return accommodation;
    }

}
