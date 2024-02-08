package com.example.iworks.global.repository;

import com.example.iworks.global.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, Integer> {

    Code findCodeByCodeName(String name);
    Code findCodeByCodeId(Integer codeId);
}
