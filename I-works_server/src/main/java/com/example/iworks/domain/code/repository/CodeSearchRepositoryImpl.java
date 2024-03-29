package com.example.iworks.domain.code.repository;

import com.example.iworks.domain.code.dto.CodeResponseDto;
import com.example.iworks.domain.code.entity.QCode;
import com.example.iworks.domain.code.repository.custom.CodeSearchRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CodeSearchRepositoryImpl implements CodeSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<CodeResponseDto> getCodeGroupAll(int codeGroupId) {
        QCode c = new QCode("c");
        return jpaQueryFactory
                .select(c)
                .from(c)
                .where(
                        c.codeIsUse.isTrue()
                                .and(c.codeGroup.codeGroupId.eq(codeGroupId)))
                .stream()
                .map(CodeResponseDto::new).toList();
    }
}
