package com.example.petswithai.controller;

import com.example.petswithai.config.SecurityConfig;
import com.example.petswithai.dto.PetNameBreedDTO;
import com.example.petswithai.entity.Pet;
import com.example.petswithai.service.PetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
@Import(SecurityConfig.class)
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllPets() throws Exception {
        Pet pet1 = new Pet("Buddy", "Dog", "Labrador", 5, null);
        Pet pet2 = new Pet("Mittens", "Cat", "Siamese", 3, null);
        List<Pet> pets = Arrays.asList(pet1, pet2);

        Mockito.when(petService.getAllPets()).thenReturn(pets);

        mockMvc.perform(get("/pets").with(httpBasic("admin", "adminPass")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Buddy"))
                .andExpect(jsonPath("$[1].name").value("Mittens"));
    }

    @Test
    public void testGetPetById() throws Exception {
        Pet pet = new Pet("Buddy", "Dog", "Labrador", 5, null);

        Mockito.when(petService.getPetById(1)).thenReturn(pet);

        mockMvc.perform(get("/pets/1").with(httpBasic("admin", "adminPass")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Buddy"));
    }

    @Test
    public void testCreatePet() throws Exception {
        Pet pet = new Pet("Buddy", "Dog", "Labrador", 5, null);

        Mockito.when(petService.createPet(Mockito.any(Pet.class))).thenReturn(pet);

        mockMvc.perform(post("/pets").with(httpBasic("admin", "adminPass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pet)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Buddy"));
    }

    @Test
    public void testUpdatePet() throws Exception {
        Pet pet = new Pet("Buddy", "Dog", "Labrador", 5, null);

        Mockito.when(petService.updatePet(Mockito.eq(1), Mockito.any(Pet.class))).thenReturn(pet);

        mockMvc.perform(put("/pets/1").with(httpBasic("admin", "adminPass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pet)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Buddy"));
    }

    @Test
    public void testDeletePetById() throws Exception {
        Mockito.doNothing().when(petService).deletePetById(1);

        mockMvc.perform(delete("/pets/1").with(httpBasic("admin", "adminPass")))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFindPetsByAnimalType() throws Exception {
        Pet pet1 = new Pet("Buddy", "Dog", "Labrador", 5, null);
        List<Pet> pets = Arrays.asList(pet1);

        Mockito.when(petService.findPetsByAnimalType("Dog")).thenReturn(pets);

        mockMvc.perform(get("/pets/type/Dog").with(httpBasic("admin", "adminPass")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Buddy"));
    }

    @Test
    public void testFindPetsByBreed() throws Exception {
        Pet pet1 = new Pet("Buddy", "Dog", "Labrador", 5, null);
        List<Pet> pets = Arrays.asList(pet1);

        Mockito.when(petService.findPetsByBreed("Labrador")).thenReturn(pets);

        mockMvc.perform(get("/pets/breed/Labrador").with(httpBasic("admin", "adminPass")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Buddy"));
    }

    @Test
    public void testGetNameAndBreed() throws Exception {
        PetNameBreedDTO petAndBreedDTO = new PetNameBreedDTO("Buddy", "Dog", "Labrador");
        List<PetNameBreedDTO> petAndBreedDTOs = Arrays.asList(petAndBreedDTO);

        Mockito.when(petService.getNameAndBreed()).thenReturn(petAndBreedDTOs);

        mockMvc.perform(get("/pets/name-breed").with(httpBasic("admin", "adminPass")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Buddy"))
                .andExpect(jsonPath("$[0].breed").value("Labrador"));
    }

    @Test
    public void testGetPetStatistics() throws Exception {
        String statistics = "Statistics data";
        Mockito.when(petService.getPetStatistics()).thenReturn(statistics);

        mockMvc.perform(get("/pets/statistics").with(httpBasic("admin", "adminPass")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(statistics));
    }

}