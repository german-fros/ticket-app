package com.project.ticket_app.infra.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TenantContextTest {

    @Test
    @DisplayName("Should expose tenant ID inside execution scope and clear it outside")
    void shouldExposeTenantIdOnlyInsideScope() {
        String tenantId = "tenant-a";

        assertThat(TenantContext.getTenantId()).isNull();

        TenantContext.runWithTenant(tenantId, () -> assertThat(TenantContext.getTenantId()).isEqualTo(tenantId)
        );

        assertThat(TenantContext.getTenantId()).isNull();
    }
}
