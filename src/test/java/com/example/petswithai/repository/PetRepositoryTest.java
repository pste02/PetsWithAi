package com.example.petswithai.repository;

import com.example.petswithai.entity.Household;
import com.example.petswithai.entity.Pet;
import com.example.petswithai.dto.PetNameBreedDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private HouseholdRepository householdRepository;

    @BeforeEach
    void setUp() {
        petRepository.deleteAll();
        Household h1 = new Household("EIR123", 2, 4, true);
        householdRepository.save(h1);
        petRepository.save(new Pet("Buddy", "Dog", "Labrador", 5, h1));
        petRepository.save(new Pet("Mittens", "Cat", "Siamese", 3, h1));
    }

    @Test
    void testFindByAnimalType() {
        List<Pet> dogs = petRepository.findByAnimalType("Dog");
        assertEquals(1, dogs.size());
        assertEquals("Buddy", dogs.get(0).getName());
    }

    @Test
    void testFindByBreedOrderByAge() {
        List<Pet> siameseCats = petRepository.findByBreedOrderByAge("Siamese");
        assertEquals(1, siameseCats.size());
        assertEquals("Mittens", siameseCats.get(0).getName());
    }

    @Test
    void testFindByNameIgnoreCase() {
        List<Pet> pets = petRepository.findByNameIgnoreCase("buddy");
        assertEquals(1, pets.size());
        assertEquals("Buddy", pets.get(0).getName());
    }

    @Test
    void testDeleteByNameIgnoreCase() {
        petRepository.deleteByNameIgnoreCase("buddy");
        assertFalse(petRepository.existsById(1));
    }

    @Test
    void testFindNameAndBreed() {
        List<PetNameBreedDTO> nameBreedList = petRepository.findNameAndBreed();
        assertEquals(2, nameBreedList.size());
    }

    @Test
    void testFindAverageAge() {
        Double averageAge = petRepository.findAverageAge();
        assertEquals(4.0, averageAge);
    }

    @Test
    void testFindOldestAge() {
        Integer oldestAge = petRepository.findOldestAge();
        assertEquals(5, oldestAge);
    }
}