package com.example.petswithai.repository;

import com.example.petswithai.dto.PetNameBreedDTO;
import com.example.petswithai.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findByAnimalType(String animalType);
    List<Pet> findByBreedOrderByAge(String breed);
    List<Pet> findByNameIgnoreCase(String name);
    void deleteByNameIgnoreCase(String name);

    @Query("SELECT new com.example.petswithai.dto.PetNameBreedDTO(p.name, p.animalType, p.breed) FROM Pet p")
    List<PetNameBreedDTO> findNameAndBreed();

    @Query("SELECT AVG(p.age) FROM Pet p")
    Double findAverageAge();

    @Query("SELECT MAX(p.age) FROM Pet p")
    Integer findOldestAge();
}