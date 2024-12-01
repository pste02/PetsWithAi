package com.example.petswithai.controller;

import com.example.petswithai.config.SecurityConfig;
import com.example.petswithai.entity.User;
import com.example.petswithai.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateUser() throws Exception {
        User user = new User("newuser", "newpass", "ROLE_USER", true);

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .with(httpBasic("admin", "adminPass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"))
                .andExpect(jsonPath("$.unlocked").value(true));
    }

    @Test
    public void testResetPassword() throws Exception {
        User user = new User("existinguser", "newpass", "ROLE_USER", true);

        Mockito.when(userService.resetPassword(Mockito.eq(1L), Mockito.anyString())).thenReturn(user);

        mockMvc.perform(put("/users/1/reset-password")
                        .with(httpBasic("admin", "adminPass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("newpass")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("existinguser"))
                .andExpect(jsonPath("$.password").value("newpass"));
    }

    @Test
    public void testToggleUnlocked() throws Exception {
        User user = new User("existinguser", "existingpass", "ROLE_USER", false);

        Mockito.when(userService.toggleUnlocked(1L)).thenReturn(user);

        mockMvc.perform(put("/users/1/toggle-unlocked")
                        .with(httpBasic("admin", "adminPass")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("existinguser"))
                .andExpect(jsonPath("$.unlocked").value(false));
    }

    @Test
    public void testDeleteUser() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/users/1")
                        .with(httpBasic("admin", "adminPass")))
                .andExpect(status().isNoContent());
    }
}