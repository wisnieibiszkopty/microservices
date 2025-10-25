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

    public List<Room> getAll(){
        return roomRepository.findAll();
    }

    public List<Room> getAllByPriceRange(BigDecimal minPrice, BigDecimal maxPrice){
        return roomRepository.findByPriceRange(minPrice, maxPrice);
    }

    public List<Room> getAllByGuestCountAndAccommodationId(Long accommodationId, Integer guestsCount){
        return roomRepository.findAvailableRooms(guestsCount, accommodationId);
    }

    public Room create(CreateRoomRequest request){
        var room = createRequestToModel(request);
        var accommodation = accommodationService.getById(room.getAccommodationId());

        if(accommodation == null){
            throw new AccommodationNotFoundException("Accommodation not found");
        }

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

        var room = updateRequestToModel(id, request);
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
        room.setBedsCount(request.bedsCount());
        room.setOccupied(request.occupied());
        room.setAccommodationId(request.accommodationId());

        return room;
    }

    private Room updateRequestToModel(Long id, UpdateRoomRequest request){
        var room = new Room();
        room.setId(id);
        room.setNumber(request.number());
        room.setArea(request.area());
        room.setFloor(request.floor());
        room.setPrice(request.price());
        room.setGuestsCount(request.guestsCount());
        room.setBedsCount(request.bedsCount());
        room.setOccupied(request.occupied());
        room.setAccommodationId(request.accommodationId());

        return room;
    }
}
