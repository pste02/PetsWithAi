package com.example.petswithai.controller;

import com.example.petswithai.config.SecurityConfig;
import com.example.petswithai.entity.Household;
import com.example.petswithai.service.HouseholdService;
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

@WebMvcTest(HouseholdController.class)
@Import(SecurityConfig.class)
public class HouseholdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseholdService householdService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllHouseholds() throws Exception {
        Household household1 = new Household("EIR123", 2, 4, true);
        Household household2 = new Household("EIR456", 0, 3, false);
        List<Household> households = Arrays.asList(household1, household2);

        Mockito.when(householdService.getAllHouseholds()).thenReturn(households);

        mockMvc.perform(get("/households").with(httpBasic("admin", "adminPass")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].eircode").value("EIR123"))
                .andExpect(jsonPath("$[0].numberOfOccupants").value(2))
                .andExpect(jsonPath("$[0].maxNumberOfOccupants").value(4))
                .andExpect(jsonPath("$[0].ownerOccupied").value(true))
                .andExpect(jsonPath("$[1].eircode").value("EIR456"))
                .andExpect(jsonPath("$[1].numberOfOccupants").value(0))
                .andExpect(jsonPath("$[1].maxNumberOfOccupants").value(3))
                .andExpect(jsonPath("$[1].ownerOccupied").value(false));
    }

    @Test
    public void testGetHouseholdById() throws Exception {
        Household household = new Household("EIR123", 2, 4, true);

        Mockito.when(householdService.getHouseholdById("EIR123", false)).thenReturn(household);

        mockMvc.perform(get("/households/EIR123").with(httpBasic("admin", "adminPass")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eircode").value("EIR123"))
                .andExpect(jsonPath("$.numberOfOccupants").value(2))
                .andExpect(jsonPath("$.maxNumberOfOccupants").value(4))
                .andExpect(jsonPath("$.ownerOccupied").value(true));
    }

    @Test
    public void testCreateHousehold() throws Exception {
        Household household = new Household("EIR123", 2, 4, true);

        Mockito.when(householdService.createHousehold(Mockito.any(Household.class))).thenReturn(household);

        mockMvc.perform(post("/households").with(httpBasic("user", "userPass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(household)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eircode").value("EIR123"))
                .andExpect(jsonPath("$.numberOfOccupants").value(2))
                .andExpect(jsonPath("$.maxNumberOfOccupants").value(4))
                .andExpect(jsonPath("$.ownerOccupied").value(true));
    }

    @Test
    public void testUpdateHousehold() throws Exception {
        Household household = new Household("EIR123", 2, 4, true);

        Mockito.when(householdService.updateHousehold(Mockito.eq("EIR123"), Mockito.any(Household.class))).thenReturn(household);

        mockMvc.perform(put("/households/EIR123").with(httpBasic("admin", "adminPass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(household)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eircode").value("EIR123"))
                .andExpect(jsonPath("$.numberOfOccupants").value(2))
                .andExpect(jsonPath("$.maxNumberOfOccupants").value(4))
                .andExpect(jsonPath("$.ownerOccupied").value(true));
    }

    @Test
    public void testDeleteHouseholdById() throws Exception {
        Mockito.doNothing().when(householdService).deleteHouseholdById("EIR123");

        mockMvc.perform(delete("/households/EIR123").with(httpBasic("admin", "adminPass")))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFindHouseholdsWithNoPets() throws Exception {
        Household household = new Household("EIR456", 0, 3, false);
        List<Household> households = Arrays.asList(household);

        Mockito.when(householdService.findHouseholdsWithNoPets()).thenReturn(households);

        mockMvc.perform(get("/households/no-pets").with(httpBasic("admin", "adminPass")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].eircode").value("EIR456"))
                .andExpect(jsonPath("$[0].numberOfOccupants").value(0))
                .andExpect(jsonPath("$[0].maxNumberOfOccupants").value(3))
                .andExpect(jsonPath("$[0].ownerOccupied").value(false));
    }

    @Test
    public void testFindOwnerOccupiedHouseholds() throws Exception {
        Household household = new Household("EIR123", 2, 4, true);
        List<Household> households = Arrays.asList(household);

        Mockito.when(householdService.findOwnerOccupiedHouseholds()).thenReturn(households);

        mockMvc.perform(get("/households/owner-occupied").with(httpBasic("admin", "adminPass")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].eircode").value("EIR123"))
                .andExpect(jsonPath("$[0].numberOfOccupants").value(2))
                .andExpect(jsonPath("$[0].maxNumberOfOccupants").value(4))
                .andExpect(jsonPath("$[0].ownerOccupied").value(true));
    }

    @Test
    public void testGetHouseholdByEircode() throws Exception {
        Household household = new Household("EIR123", 2, 4, true);

        Mockito.when(householdService.getHouseholdById("EIR123", true)).thenReturn(household);

        mockMvc.perform(get("/households/eircode/EIR123").with(httpBasic("admin", "adminPass")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eircode").value("EIR123"))
                .andExpect(jsonPath("$.numberOfOccupants").value(2))
                .andExpect(jsonPath("$.maxNumberOfOccupants").value(4))
                .andExpect(jsonPath("$.ownerOccupied").value(true));
    }

    @Test
    public void testGetHouseholdStatistics() throws Exception {
        String statistics = "Statistics data";
        Mockito.when(householdService.getHouseholdStatistics()).thenReturn(statistics);

        mockMvc.perform(get("/households/statistics").with(httpBasic("admin", "adminPass")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(statistics));
    }

    @Test
    public void testDeleteHouseholdByEircode() throws Exception {
        Mockito.doNothing().when(householdService).deleteHouseholdById("EIR123");

        mockMvc.perform(delete("/households/eircode/EIR123").with(httpBasic("admin", "adminPass")))
                .andExpect(status().isNoContent());
    }


}