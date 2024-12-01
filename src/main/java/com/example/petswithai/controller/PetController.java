package com.example.petswithai.controller;

import com.example.petswithai.dto.PetNameBreedDTO;
import com.example.petswithai.entity.Pet;
import com.example.petswithai.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
        Pet createdPet = petService.createPet(pet);
        return ResponseEntity.ok(createdPet);
    }

    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> pets = petService.getAllPets();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable int id) {
        Pet pet = petService.getPetById(id);
        return ResponseEntity.ok(pet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable int id, @RequestBody Pet pet) {
        Pet updatedPet = petService.updatePet(id, pet);
        return ResponseEntity.ok(updatedPet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetById(@PathVariable int id) {
        petService.deletePetById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{animalType}")
    public ResponseEntity<List<Pet>> findPetsByAnimalType(@PathVariable String animalType) {
        List<Pet> pets = petService.findPetsByAnimalType(animalType);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/breed/{breed}")
    public ResponseEntity<List<Pet>> findPetsByBreed(@PathVariable String breed) {
        List<Pet> pets = petService.findPetsByBreed(breed);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/name-breed")
    public ResponseEntity<List<PetNameBreedDTO>> getNameAndBreed() {
        List<PetNameBreedDTO> nameBreedList = petService.getNameAndBreed();
        return ResponseEntity.ok(nameBreedList);
    }

    @GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPetStatistics() {
        String statistics = petService.getPetStatistics();
        return ResponseEntity.ok(statistics);
    }

}