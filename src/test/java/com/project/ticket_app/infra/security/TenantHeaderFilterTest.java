package com.project.ticket_app.infra.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DummyEventController.class)
@Import(TenantHeaderFilter.class)
@WithMockUser
public class TenantHeaderFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return 400 Bad Request when 'X-Tenant-ID' header is missing")
    void whenTenantHeaderIsMissing_shouldReturn400BadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/events"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Missing Tenant Identifier"))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    @DisplayName("Should return 200 OK when 'X-Tenant-ID' header is present")
    void whenTenantHeaderIsPresent_shouldReturn200OK() throws Exception {
        mockMvc.perform(get("/api/v1/events")
                        .header("X-Tenant-ID", "tenant-alpha"))
                .andExpect(status().isOk());
    }
}
