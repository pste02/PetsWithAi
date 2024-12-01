package com.example.petswithai.service;

import com.example.petswithai.entity.Household;
import com.example.petswithai.exception.BadDataException;
import com.example.petswithai.exception.NotFoundException;
import com.example.petswithai.repository.HouseholdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseholdServiceImpl implements HouseholdService {

    private final HouseholdRepository householdRepository;

    @Override
    public Household createHousehold(Household household) {
        if (household.getNumberOfOccupants() < 0 || household.getMaxNumberOfOccupants() < 0) {
            throw new BadDataException("Number of occupants cannot be negative");
        }
        return householdRepository.save(household);
    }

    @Override
    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    @Override
    public Household getHouseholdById(String eircode, boolean includePets) {
        Household household = householdRepository.findById(eircode)
                .orElseThrow(() -> new NotFoundException("Household not found with eircode: " + eircode));
        if (!includePets) {
            household.setPets(null);
        }
        return household;
    }

    @Override
    public Household updateHousehold(String eircode, Household household) {
        Household existingHousehold = getHouseholdById(eircode, true);
        if (household.getNumberOfOccupants() < 0 || household.getMaxNumberOfOccupants() < 0) {
            throw new BadDataException("Number of occupants cannot be negative");
        }
        existingHousehold.setNumberOfOccupants(household.getNumberOfOccupants());
        existingHousehold.setMaxNumberOfOccupants(household.getMaxNumberOfOccupants());
        existingHousehold.setOwnerOccupied(household.isOwnerOccupied());
        return householdRepository.save(existingHousehold);
    }

    @Override
    public void deleteHouseholdById(String eircode) {
        if (!householdRepository.existsById(eircode)) {
            throw new NotFoundException("Household not found with eircode: " + eircode);
        }
        householdRepository.deleteById(eircode);
    }

    @Override
    public List<Household> findHouseholdsWithNoPets() {
        return householdRepository.findHouseholdsWithNoPets();
    }

    @Override
    public List<Household> findOwnerOccupiedHouseholds() {
        return householdRepository.findByOwnerOccupied(true);
    }

    @Override
    public String getHouseholdStatistics() {
        Long emptyHouseholds = householdRepository.countEmptyHouseholds();
        Long fullHouseholds = householdRepository.countFullHouseholds();

        return String.format("Household Statistics: Empty Households = %d, Full Households = %d",
                emptyHouseholds, fullHouseholds);
    }
}