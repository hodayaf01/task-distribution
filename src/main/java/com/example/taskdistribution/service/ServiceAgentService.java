package com.example.taskdistribution.service;

import com.example.taskdistribution.entity.ServiceAgent;
import com.example.taskdistribution.repository.ServiceAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceAgentService {

    @Autowired
    private ServiceAgentRepository serviceAgentRepository;
    @Autowired
    private TaskService taskService;

    public ServiceAgent updateAvailability(Long id, boolean isAvailable) {
        ServiceAgent agent = serviceAgentRepository.findById(id).orElseThrow();
        agent.setAvailable(isAvailable);

        if (isAvailable) {
            taskService.reassignPendingTasks(agent);
        }

        return serviceAgentRepository.save(agent);
    }

    public void deleteAgent(Long id) {
        taskService.reassignTasksFromDeletedAgent(id);
        serviceAgentRepository.deleteById(id);
    }

    public List<ServiceAgent> getAllAgents() {
        return serviceAgentRepository.findAll();
    }

    public ServiceAgent addAgent(ServiceAgent agent) {
        return serviceAgentRepository.save(agent);
    }
}
