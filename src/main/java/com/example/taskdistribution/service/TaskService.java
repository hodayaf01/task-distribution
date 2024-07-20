package com.example.taskdistribution.service;

import com.example.taskdistribution.entity.ServiceAgent;
import com.example.taskdistribution.entity.Task;
import com.example.taskdistribution.repository.ServiceAgentRepository;
import com.example.taskdistribution.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ServiceAgentRepository serviceAgentRepository;

    public Task createTask(Task task) {
        Optional<ServiceAgent> agent = serviceAgentRepository.findAll().stream()
                .filter(ServiceAgent::isAvailable)
                .sorted((a1, a2) -> Integer.compare(a1.getTaskCount(), a2.getTaskCount()))
                .findFirst();

        if (agent.isPresent()) {
            task.setAssignedAgentId(agent.get().getId());
            task.setStatus("Assigned");
            agent.get().setTaskCount(agent.get().getTaskCount() + 1);
            serviceAgentRepository.save(agent.get());
        } else {
            task.setStatus("Pending");
        }

        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void reassignPendingTasks(ServiceAgent agent) {
        List<Task> pendingTasks = taskRepository.findByStatus("Pending");

        for (Task task : pendingTasks) {
            task.setAssignedAgentId(agent.getId());
            task.setStatus("Assigned");
            agent.setTaskCount(agent.getTaskCount() + 1);
            taskRepository.save(task);

            if (!agent.isAvailable()) {
                break;
            }
        }

        serviceAgentRepository.save(agent);
    }
}
