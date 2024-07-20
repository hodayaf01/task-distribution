package com.example.taskdistribution.repository;

import com.example.taskdistribution.entity.ServiceAgent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceAgentRepository extends JpaRepository<ServiceAgent, Long> {
}