package com.example.petswithai.service;

import com.example.petswithai.entity.Household;
import com.example.petswithai.exception.BadDataException;
import com.example.petswithai.exception.NotFoundException;
import com.example.petswithai.repository.HouseholdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HouseholdServiceTest {

    @Mock
    private HouseholdRepository householdRepository;

    @InjectMocks
    private HouseholdServiceImpl householdService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateHousehold() {
        Household household = new Household("EIR123", 2, 4, true);
        when(householdRepository.save(household)).thenReturn(household);

        Household createdHousehold = householdService.createHousehold(household);
        assertEquals(household, createdHousehold);
    }

    @Test
    void testCreateHouseholdWithNegativeOccupants() {
        Household household = new Household("EIR123", -1, 4, true);

        assertThrows(BadDataException.class, () -> householdService.createHousehold(household));
    }

    @Test
    void testGetAllHouseholds() {
        Household household = new Household("EIR123", 2, 4, true);
        when(householdRepository.findAll()).thenReturn(List.of(household));

        List<Household> households = householdService.getAllHouseholds();
        assertEquals(1, households.size());
        assertEquals("EIR123", households.get(0).getEircode());
    }

    @Test
    void testGetHouseholdById() {
        Household household = new Household("EIR123", 2, 4, true);
        when(householdRepository.findById("EIR123")).thenReturn(Optional.of(household));

        Household foundHousehold = householdService.getHouseholdById("EIR123", false);
        assertEquals(household, foundHousehold);
    }

    @Test
    void testGetHouseholdByIdNotFound() {
        when(householdRepository.findById("EIR123")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> householdService.getHouseholdById("EIR123", false));
    }

    @Test
    void testUpdateHousehold() {
        Household household = new Household("EIR123", 2, 4, true);
        when(householdRepository.findById("EIR123")).thenReturn(Optional.of(household));
        when(householdRepository.save(household)).thenReturn(household);

        Household updatedHousehold = householdService.updateHousehold("EIR123", household);
        assertEquals(household, updatedHousehold);
    }

    @Test
    void testUpdateHouseholdWithNegativeOccupants() {
        Household household = new Household("EIR123", -1, 4, true);
        when(householdRepository.findById("EIR123")).thenReturn(Optional.of(household));

        assertThrows(BadDataException.class, () -> householdService.updateHousehold("EIR123", household));
    }

    @Test
    void testDeleteHouseholdById() {
        when(householdRepository.existsById("EIR123")).thenReturn(true);
        doNothing().when(householdRepository).deleteById("EIR123");

        householdService.deleteHouseholdById("EIR123");
        verify(householdRepository, times(1)).deleteById("EIR123");
    }

    @Test
    void testDeleteHouseholdByIdNotFound() {
        when(householdRepository.existsById("EIR123")).thenReturn(false);

        assertThrows(NotFoundException.class, () -> householdService.deleteHouseholdById("EIR123"));
    }

    @Test
    void testFindHouseholdsWithNoPets() {
        Household household = new Household("EIR456", 0, 3, false);
        when(householdRepository.findHouseholdsWithNoPets()).thenReturn(List.of(household));

        List<Household> households = householdService.findHouseholdsWithNoPets();
        assertEquals(1, households.size());
        assertEquals("EIR456", households.get(0).getEircode());
    }

    @Test
    void testFindOwnerOccupiedHouseholds() {
        Household household = new Household("EIR123", 2, 4, true);
        when(householdRepository.findByOwnerOccupied(true)).thenReturn(List.of(household));

        List<Household> ownerOccupiedHouseholds = householdService.findOwnerOccupiedHouseholds();
        assertEquals(1, ownerOccupiedHouseholds.size());
        assertEquals("EIR123", ownerOccupiedHouseholds.get(0).getEircode());
    }
}