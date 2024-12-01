package com.example.petswithai.service;

import com.example.petswithai.dto.PetNameBreedDTO;
import com.example.petswithai.entity.Pet;

import java.util.List;

public interface PetService {
    Pet createPet(Pet pet);
    List<Pet> getAllPets();
    Pet getPetById(int id);
    Pet updatePet(int id, Pet pet);
    void deletePetById(int id);
    List<Pet> findPetsByAnimalType(String animalType);
    List<Pet> findPetsByBreed(String breed);
    List<PetNameBreedDTO> getNameAndBreed();
    String getPetStatistics();
}