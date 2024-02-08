package com.example.iworks.global.repository;

import com.example.iworks.global.entity.CodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeGroupRepository extends JpaRepository<CodeGroup, Integer> {
    CodeGroup findByCodeGroupName(String name);
}
