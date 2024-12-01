package com.example.petswithai.controller;

import com.example.petswithai.PetsWithAiApplication;
import com.example.petswithai.config.SecurityConfig;
import com.example.petswithai.entity.Pet;
import com.example.petswithai.entity.Household;
import com.example.petswithai.service.PetService;
import com.example.petswithai.service.HouseholdService;
import com.example.petswithai.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

@SpringBootTest(classes = PetsWithAiApplication.class)
@AutoConfigureHttpGraphQlTester
@Import(SecurityConfig.class)
@Rollback
public class GraphQLTests {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private PetService petService;

    @MockBean
    private HouseholdService householdService;

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetPetById() throws NotFoundException {
        Pet pet = new Pet("Buddy", "Dog", "Golden Retriever", 3, null);
        when(petService.getPetById(1)).thenReturn(pet);
        this.graphQlTester
                .documentName("getPetById")
                .variable("id", 1)
                .execute()
                .path("getPetById")
                .entity(Pet.class)
                .isEqualTo(pet);
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetHouseholdByEircode() throws NotFoundException {
        Household household = new Household("D01A1A1", 3, 5, true);
        when(householdService.getHouseholdById("D01A1A1", true)).thenReturn(household);
        this.graphQlTester
                .documentName("getHouseholdByEircode")
                .variable("eircode", "D01A1A1")
                .execute()
                .path("getHouseholdByEircode")
                .entity(Household.class)
                .isEqualTo(household);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deletePetById() throws NotFoundException {
        doNothing().when(petService).deletePetById(1);
        this.graphQlTester
                .documentName("deletePetById")
                .variable("id", 1)
                .execute()
                .path("deletePetById")
                .entity(Boolean.class)
                .isEqualTo(true);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteHouseholdByEircode() throws NotFoundException {
        doNothing().when(householdService).deleteHouseholdById("D01A1A1");
        this.graphQlTester
                .documentName("deleteHouseholdByEircode")
                .variable("eircode", "D01A1A1")
                .execute()
                .path("deleteHouseholdByEircode")
                .entity(Boolean.class)
                .isEqualTo(true);
    }
}