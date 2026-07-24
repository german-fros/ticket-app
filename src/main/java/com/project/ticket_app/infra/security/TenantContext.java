package com.project.ticket_app.infra.security;

public class TenantContext {

    public static final ScopedValue<String> TENANT_KEY = ScopedValue.newInstance();

    public static String getTenantId() {
        if (TENANT_KEY.isBound()) {
            return TENANT_KEY.get();
        } else {
            return null;
        }
    }

    public static void runWithTenant(String tenantId, Runnable runnable) {
        ScopedValue.where(TENANT_KEY, tenantId)
                .run(runnable);
    }
}
