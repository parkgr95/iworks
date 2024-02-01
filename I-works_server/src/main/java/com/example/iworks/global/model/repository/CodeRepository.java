package com.example.iworks.global.model.repository;

import com.example.iworks.global.model.entity.Code;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, Integer> {

    Code findCodeByCodeName(String name);
    Code findCodeByCodeId(Integer codeId);
}
