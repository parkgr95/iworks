package com.example.iworks.domain.team.repository;

import com.example.iworks.domain.team.domain.Team;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
