package com.example.petswithai.service;

import com.example.petswithai.entity.Household;
import com.example.petswithai.entity.Pet;
import com.example.petswithai.exception.BadDataException;
import com.example.petswithai.exception.NotFoundException;
import com.example.petswithai.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl petService;

    private Household household;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        household = new Household("EIR123", 2, 4, true);
    }

    @Test
    void testCreatePet() {
        Pet pet = new Pet("Buddy", "Dog", "Labrador", 5, household);
        when(petRepository.save(pet)).thenReturn(pet);

        Pet createdPet = petService.createPet(pet);
        assertEquals(pet, createdPet);
    }

    @Test
    void testCreatePetWithNegativeAge() {
        Pet pet = new Pet("Buddy", "Dog", "Labrador", -1, household);

        assertThrows(BadDataException.class, () -> petService.createPet(pet));
    }

    @Test
    void testGetAllPets() {
        Pet pet = new Pet("Buddy", "Dog", "Labrador", 5, household);
        when(petRepository.findAll()).thenReturn(List.of(pet));

        List<Pet> pets = petService.getAllPets();
        assertEquals(1, pets.size());
        assertEquals("Buddy", pets.get(0).getName());
    }

    @Test
    void testGetPetById() {
        Pet pet = new Pet("Buddy", "Dog", "Labrador", 5, household);
        when(petRepository.findById(1)).thenReturn(Optional.of(pet));

        Pet foundPet = petService.getPetById(1);
        assertEquals(pet, foundPet);
    }

    @Test
    void testGetPetByIdNotFound() {
        when(petRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> petService.getPetById(1));
    }

    @Test
    void testUpdatePet() {
        Pet pet = new Pet("Buddy", "Dog", "Labrador", 5, household);
        when(petRepository.findById(1)).thenReturn(Optional.of(pet));
        when(petRepository.save(pet)).thenReturn(pet);

        Pet updatedPet = petService.updatePet(1, pet);
        assertEquals(pet, updatedPet);
    }

    @Test
    void testUpdatePetWithNegativeAge() {
        Pet pet = new Pet("Buddy", "Dog", "Labrador", -1, household);
        when(petRepository.findById(1)).thenReturn(Optional.of(pet));

        assertThrows(BadDataException.class, () -> petService.updatePet(1, pet));
    }

    @Test
    void testDeletePetById() {
        when(petRepository.existsById(1)).thenReturn(true);
        doNothing().when(petRepository).deleteById(1);

        petService.deletePetById(1);
        verify(petRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeletePetByIdNotFound() {
        when(petRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> petService.deletePetById(1));
    }

    @Test
    void testFindByAnimalType() {
        Pet pet = new Pet("Buddy", "Dog", "Labrador", 5, household);
        when(petRepository.findByAnimalType("Dog")).thenReturn(List.of(pet));

        List<Pet> dogs = petService.findPetsByAnimalType("Dog");
        assertEquals(1, dogs.size());
        assertEquals("Buddy", dogs.get(0).getName());
    }

    @Test
    void testFindByBreedOrderByAge() {
        Pet pet = new Pet("Mittens", "Cat", "Siamese", 3, household);
        when(petRepository.findByBreedOrderByAge("Siamese")).thenReturn(List.of(pet));

        List<Pet> siameseCats = petService.findPetsByBreed("Siamese");
        assertEquals(1, siameseCats.size());
        assertEquals("Mittens", siameseCats.get(0).getName());
    }


}