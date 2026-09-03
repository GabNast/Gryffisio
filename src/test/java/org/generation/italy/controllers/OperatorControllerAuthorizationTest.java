package org.generation.italy.controllers;

import org.generation.italy.model.dto.OperatorDto;
import org.generation.italy.services.OperatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OperatorController.class)
class OperatorControllerAuthorizationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OperatorService operatorService;

    @TestConfiguration
    @EnableMethodSecurity
    static class MethodSecurityTestConfig {
    }

    private static final String CREATE_USER_JSON = """
            {
                "firstName": "Mario",
                "lastName": "Rossi",
                "email": "mario.rossi@example.com",
                "password": "Str0ng!Passw0rd",
                "role": "OPERATOR"
            }
            """;

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_asAdmin_isAllowed() throws Exception {
        when(operatorService.createOperator(any()))
                .thenReturn(new OperatorDto(1, "Mario", "Rossi", "mario.rossi@example.com", "OPERATOR"));

        mockMvc.perform(post("/api/operators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CREATE_USER_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    void create_asOperator_isForbidden() throws Exception {
        mockMvc.perform(post("/api/operators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CREATE_USER_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_asAdmin_isAllowed() throws Exception {
        mockMvc.perform(delete("/api/operators/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    void delete_asOperator_isForbidden() throws Exception {
        mockMvc.perform(delete("/api/operators/{id}", 1))
                .andExpect(status().isForbidden());
    }
}
