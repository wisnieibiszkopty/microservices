package com.example.accommodationservice.room;

import com.example.accommodationservice.accommodation.AccommodationService;
import com.example.accommodationservice.accommodation.exceptions.AccommodationNotFoundException;
import com.example.accommodationservice.room.dtos.CreateRoomRequest;
import com.example.accommodationservice.room.dtos.UpdateRoomRequest;
import com.example.accommodationservice.room.exceptions.RoomNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final AccommodationService accommodationService;

    public Room getById(Long id){
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));
    }

    public List<Room> getAll(BigDecimal minPrice, BigDecimal maxPrice, Boolean occupied, Integer guestsCount){
        return roomRepository.getAll(minPrice, maxPrice, occupied, guestsCount);
    }

    public Room create(CreateRoomRequest request){
        var room = createRequestToModel(request);
        var accommodation = accommodationService.getById(room.getAccommodationId());
        room.setAccommodation(accommodation);
        return roomRepository.save(room);
    }

    public Room changeOccupation(Long id, Boolean occupied){
        var room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));
        room.setOccupied(occupied);
        return roomRepository.save(room);
    }

    public Room update(UpdateRoomRequest request, Long id){
        var exists = roomRepository.existsById(id);
        if(!exists){
            throw new RoomNotFoundException("Room with this id does not exist");
        }

        var room = updateRequestToModel(request);
        return roomRepository.save(room);
    }

    public void delete(Long id){
        var exists = roomRepository.existsById(id);
        if(!exists){
            throw new RoomNotFoundException("Room with this id does not exist");
        }

        roomRepository.deleteById(id);
    }

    private Room createRequestToModel(CreateRoomRequest request){
        var room = new Room();
        room.setNumber(request.number());
        room.setArea(request.area());
        room.setFloor(request.floor());
        room.setPrice(request.price());
        room.setGuestsCount(request.guestsCount());
        room.setOccupied(request.occupied());
        room.setFeatures(request.features());

        return room;
    }

    private Room updateRequestToModel(UpdateRoomRequest request){
        var room = new Room();
        room.setNumber(request.number());
        room.setArea(request.area());
        room.setFloor(request.floor());
        room.setPrice(request.price());
        room.setGuestsCount(request.guestsCount());
        room.setOccupied(request.occupied());
        room.setFeatures(request.features());

        return room;
    }
}
