package com.example.petswithai.controller;

import com.example.petswithai.dto.PetNameBreedDTO;
import com.example.petswithai.entity.Household;
import com.example.petswithai.entity.Pet;
import com.example.petswithai.service.HouseholdService;
import com.example.petswithai.service.PetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class GraphQLController {

    private HouseholdService householdService;
    private PetService petService;

    // Household Queries
    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Household> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Household getHouseholdById(@Argument String eircode) {
        return householdService.getHouseholdById(eircode, false);
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Household> findHouseholdsWithNoPets() {
        return householdService.findHouseholdsWithNoPets();
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Household> findOwnerOccupiedHouseholds() {
        return householdService.findOwnerOccupiedHouseholds();
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Pet getPetById(@Argument int id) {
        return petService.getPetById(id);
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Pet> findPetsByAnimalType(@Argument String animalType) {
        return petService.findPetsByAnimalType(animalType);
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Pet> findPetsByBreed(@Argument String breed) {
        return petService.findPetsByBreed(breed);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Household createHousehold(@Argument String eircode, @Argument int numberOfOccupants, @Argument int maxNumberOfOccupants, @Argument boolean ownerOccupied) {
        Household household = new Household(eircode, numberOfOccupants, maxNumberOfOccupants, ownerOccupied);
        return householdService.createHousehold(household);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Household updateHousehold(@Argument String eircode, @Argument int numberOfOccupants, @Argument int maxNumberOfOccupants, @Argument boolean ownerOccupied) {
        Household household = new Household(eircode, numberOfOccupants, maxNumberOfOccupants, ownerOccupied);
        return householdService.updateHousehold(eircode, household);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteHouseholdById(@Argument String eircode) {
        householdService.deleteHouseholdById(eircode);
        return true;
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Pet createPet(@Argument String name, @Argument String animalType, @Argument String breed, @Argument int age, @Argument String householdEircode) {
        Household household = householdService.getHouseholdById(householdEircode, false);
        Pet pet = new Pet(name, animalType, breed, age, household);
        return petService.createPet(pet);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Pet updatePet(@Argument int id, @Argument String name, @Argument String animalType, @Argument String breed, @Argument int age, @Argument String householdEircode) {
        Household household = householdService.getHouseholdById(householdEircode, false);
        Pet pet = new Pet(name, animalType, breed, age, household);
        return petService.updatePet(id, pet);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deletePetById(@Argument int id) {
        petService.deletePetById(id);
        return true;
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<PetNameBreedDTO> getNameAndBreed() {
        return petService.getNameAndBreed();
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String getPetStatistics() {
        return petService.getPetStatistics();
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Household getHouseholdByEircode(@Argument String eircode) {
        return householdService.getHouseholdById(eircode, true);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteHouseholdByEircode(@Argument String eircode) {
        householdService.deleteHouseholdById(eircode);
        return true;
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String getHouseholdStatistics() {
        return householdService.getHouseholdStatistics();
    }
}