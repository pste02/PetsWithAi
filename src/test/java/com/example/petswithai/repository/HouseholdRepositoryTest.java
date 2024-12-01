package com.example.petswithai.repository;

import com.example.petswithai.entity.Household;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class HouseholdRepositoryTest {

    @Autowired
    private HouseholdRepository householdRepository;

    @BeforeEach
    void setUp() {
        householdRepository.deleteAll();
        householdRepository.save(new Household("EIR123t", 2, 4, true));
        householdRepository.save(new Household("EIR456", 0, 3, false));
    }

    @Test
    void testFindHouseholdsWithNoPets() {
        List<Household> households = householdRepository.findHouseholdsWithNoPets();
        assertEquals(2, households.size());
        assertEquals("EIR123t", households.get(0).getEircode());
    }

    @Test
    void testFindByOwnerOccupied() {
        List<Household> ownerOccupiedHouseholds = householdRepository.findByOwnerOccupied(true);
        assertEquals(1, ownerOccupiedHouseholds.size());
        assertEquals("EIR123t", ownerOccupiedHouseholds.get(0).getEircode());
    }

    @Test
    void testCountEmptyHouseholds() {
        Long emptyHouseholds = householdRepository.countEmptyHouseholds();
        assertEquals(1, emptyHouseholds);
    }

    @Test
    void testCountFullHouseholds() {
        Long fullHouseholds = householdRepository.countFullHouseholds();
        assertEquals(0, fullHouseholds);
    }
}