package com.example.iworks.domain.file.repository.custom;

import com.example.iworks.domain.file.domain.File;
import com.example.iworks.global.model.entity.Code;

import java.util.Optional;

public interface FileGetRepository {

    Optional<File> findByOwner(int fileId, Code categoryCode, int ownerId);

}
