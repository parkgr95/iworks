package com.example.iworks.domain.file.repository;

import com.example.iworks.domain.file.domain.File;
import com.example.iworks.domain.file.repository.custom.FileGetRepository;
import com.example.iworks.global.model.entity.Code;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.example.iworks.domain.file.domain.QFile.file;

@RequiredArgsConstructor
public class FileRepositoryImpl implements FileGetRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<File> findByOwner(int fileId, Code code, int ownerId) {
        File findFile =  queryFactory
                .selectFrom(file)
                .where(
                        eqFileId(fileId)
                                .and(eqOwnerCategoryCode(code))
                                .and(eqOwnerId(ownerId))
                )
                .fetchOne();
        return Optional.ofNullable(findFile);
    }

    private BooleanExpression eqFileId(int fileId) {
        return file.fileId.eq(fileId);
    }

    private BooleanExpression eqOwnerCategoryCode(Code ownerCategoryCode) {
        return file.fileCategoryCode.eq(ownerCategoryCode);
    }

    private BooleanExpression eqOwnerId(int ownerId) {
        return file.fileOwnerId.eq(ownerId);
    }
}
