package com.example.iworks.domain.team.repository.team;

import com.example.iworks.domain.team.domain.Team;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {

}
