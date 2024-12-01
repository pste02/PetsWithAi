package com.example.petswithai.service;

import com.example.petswithai.entity.Household;

import java.util.List;

public interface HouseholdService {
    Household createHousehold(Household household);
    List<Household> getAllHouseholds();
    Household getHouseholdById(String eircode, boolean includePets);
    Household updateHousehold(String eircode, Household household);
    void deleteHouseholdById(String eircode);
    List<Household> findHouseholdsWithNoPets();
    List<Household> findOwnerOccupiedHouseholds();
    String getHouseholdStatistics();
}