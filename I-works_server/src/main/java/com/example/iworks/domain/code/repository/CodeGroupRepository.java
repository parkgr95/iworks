package com.example.iworks.domain.code.repository;

import com.example.iworks.domain.code.entity.CodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeGroupRepository extends JpaRepository<CodeGroup, Integer> {
    CodeGroup findByCodeGroupName(String name);
}
