package com.example.taskdistribution.controller;

import com.example.taskdistribution.entity.ServiceAgent;
import com.example.taskdistribution.service.ServiceAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
public class ServiceAgentController {
    @Autowired
    private ServiceAgentService serviceAgentService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable Long id) {
        serviceAgentService.deleteAgent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ServiceAgent createServiceAgent(@RequestBody ServiceAgent serviceAgent) {
        return serviceAgentService.addAgent(serviceAgent);
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<ServiceAgent> updateAvailability(@PathVariable Long id, @RequestParam boolean isAvailable) {
        return ResponseEntity.ok(serviceAgentService.updateAvailability(id, isAvailable));
    }

    @PostMapping
    public ResponseEntity<ServiceAgent> addAgent(@RequestBody ServiceAgent agent) {
        return ResponseEntity.ok(serviceAgentService.addAgent(agent));
    }

    @GetMapping
    public ResponseEntity<List<ServiceAgent>> getAllAgents() {
        return ResponseEntity.ok(serviceAgentService.getAllAgents());
    }
}
