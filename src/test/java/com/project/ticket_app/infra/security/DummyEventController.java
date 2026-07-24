package com.project.ticket_app.infra.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
public class DummyEventController {

    @GetMapping
    public ResponseEntity<Void> getEvents() {
        return ResponseEntity.ok().build();
    }
}
