package com.project.ticket_app.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class TenantHeaderFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws IOException {

        String HEADER_NAME = "X-Tenant-ID";

        if (request.getHeader(HEADER_NAME) == null || request.getHeader(HEADER_NAME).isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/problem+json");

            ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                    HttpStatus.BAD_REQUEST,
                    "The required header 'X-Tenant-ID' is missing."
            );
            problem.setTitle("Missing Tenant Identifier");

            String problemJson = objectMapper.writeValueAsString(problem);
            response.getWriter().write(problemJson);

            return;
        }

        String tenantId = request.getHeader(HEADER_NAME);
        TenantContext.runWithTenant(tenantId, () -> {
            try {
                filterChain.doFilter(request, response);
            } catch (IOException | ServletException e) {
                throw new RuntimeException(e); // Crear excepcion personalizada
            }
        });
    }
}
