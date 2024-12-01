package com.example.petswithai.service;

import com.example.petswithai.dto.PetNameBreedDTO;
import com.example.petswithai.entity.Pet;
import com.example.petswithai.exception.BadDataException;
import com.example.petswithai.exception.NotFoundException;
import com.example.petswithai.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    @Override
    public Pet createPet(Pet pet) {
        if (pet.getAge() < 0) {
            throw new BadDataException("Age cannot be negative");
        }
        return petRepository.save(pet);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Pet getPetById(int id) {
        return petRepository.findById(id).orElseThrow(() -> new NotFoundException("Pet not found with id: " + id));
    }

    @Override
    public Pet updatePet(int id, Pet pet) {
        Pet existingPet = getPetById(id);
        if (pet.getAge() < 0) {
            throw new BadDataException("Age cannot be negative");
        }
        existingPet.setName(pet.getName());
        existingPet.setAnimalType(pet.getAnimalType());
        existingPet.setBreed(pet.getBreed());
        existingPet.setAge(pet.getAge());
        return petRepository.save(existingPet);
    }

    @Override
    public void deletePetById(int id) {
        if (!petRepository.existsById(id)) {
            throw new NotFoundException("Pet not found with id: " + id);
        }
        petRepository.deleteById(id);
    }


    @Override
    public List<Pet> findPetsByAnimalType(String animalType) {
        return petRepository.findByAnimalType(animalType);
    }

    @Override
    public List<Pet> findPetsByBreed(String breed) {
        return petRepository.findByBreedOrderByAge(breed);
    }

    @Override
    public List<PetNameBreedDTO> getNameAndBreed() {
        return petRepository.findNameAndBreed();
    }

    @Override
    public String getPetStatistics() {
        Double averageAge = petRepository.findAverageAge();
        Integer oldestAge = petRepository.findOldestAge();
        long totalCount = petRepository.count();

        return String.format("Pet Statistics: Average Age = %.2f, Oldest Age = %d, Total Count = %d",
                averageAge, oldestAge, totalCount);
    }
}