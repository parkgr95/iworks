package com.example.iworks.domain.code.repository;

import com.example.iworks.domain.code.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, Integer> {

    Code findCodeByCodeName(String name);
    Code findCodeByCodeId(Integer codeId);
}
