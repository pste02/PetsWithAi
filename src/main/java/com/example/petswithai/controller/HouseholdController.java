package com.example.petswithai.controller;

import com.example.petswithai.entity.Household;
import com.example.petswithai.service.HouseholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/households")
public class HouseholdController {

    @Autowired
    private HouseholdService householdService;

    @PostMapping
    public ResponseEntity<Household> createHousehold(@RequestBody Household household) {
        Household createdHousehold = householdService.createHousehold(household);
        return ResponseEntity.ok(createdHousehold);
    }

    @GetMapping
    public ResponseEntity<List<Household>> getAllHouseholds() {
        List<Household> households = householdService.getAllHouseholds();
        return ResponseEntity.ok(households);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Household> getHouseholdById(@PathVariable String id) {
        Household household = householdService.getHouseholdById(id, false);
        return ResponseEntity.ok(household);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Household> updateHousehold(@PathVariable String id, @RequestBody Household household) {
        Household updatedHousehold = householdService.updateHousehold(id, household);
        return ResponseEntity.ok(updatedHousehold);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHouseholdById(@PathVariable String id) {
        householdService.deleteHouseholdById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/no-pets")
    public ResponseEntity<List<Household>> findHouseholdsWithNoPets() {
        List<Household> households = householdService.findHouseholdsWithNoPets();
        return ResponseEntity.ok(households);
    }

    @GetMapping("/owner-occupied")
    public ResponseEntity<List<Household>> findOwnerOccupiedHouseholds() {
        List<Household> households = householdService.findOwnerOccupiedHouseholds();
        return ResponseEntity.ok(households);
    }

    @GetMapping("/eircode/{eircode}")
    public ResponseEntity<Household> getHouseholdByEircode(@PathVariable String eircode) {
        Household household = householdService.getHouseholdById(eircode, true);
        return ResponseEntity.ok(household);
    }

    @GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHouseholdStatistics() {
        String statistics = householdService.getHouseholdStatistics();
        return ResponseEntity.ok(statistics);
    }

    @DeleteMapping("/eircode/{eircode}")
    public ResponseEntity<Void> deleteHouseholdByEircode(@PathVariable String eircode) {
        householdService.deleteHouseholdById(eircode);
        return ResponseEntity.noContent().build();
    }
}