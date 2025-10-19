package com.example.accommodationservice.accommodation;

import com.example.accommodationservice.accommodation.dtos.AccommodationRequest;
import com.example.accommodationservice.accommodation.dtos.AccommodationType;
import com.example.accommodationservice.accommodation.exceptions.AccommodationExistsException;
import com.example.accommodationservice.accommodation.exceptions.AccommodationNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;

    public Accommodation getById(Long id){
        return accommodationRepository
                .findById(id)
                .orElseThrow(() -> new AccommodationNotFoundException("Accommodation not found"));
    }

    public List<Accommodation> getAll(){
        return accommodationRepository.findAll();
    }

    public List<Accommodation> search(String phrase, AccommodationType type){
        return accommodationRepository.findByPhraseAndType(phrase, type);
    }

    public Accommodation create(AccommodationRequest accommodation) {
        var exists = accommodationRepository.existsByName(accommodation.name());
        if (exists) {
            throw new AccommodationExistsException("Accommodation with this name already exists");
        }

        return accommodationRepository.save(Accommodation.fromRequest(accommodation));
    }

    public Accommodation update(Long id, Accommodation accommodation){
        var exists = accommodationRepository.existsById(id);
        if(!exists){
            throw new AccommodationNotFoundException("Accommodation with this id does not exist");
        }

        return accommodationRepository.save(accommodation);
    }

    public void delete(Long id){
        var exists = accommodationRepository.existsById(id);
        if(!exists){
            throw new AccommodationNotFoundException("Accommodation with this id does not exist");
        }

        accommodationRepository.deleteById(id);
    }
}
