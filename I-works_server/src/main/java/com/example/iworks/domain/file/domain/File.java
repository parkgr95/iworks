package com.example.iworks.domain.file.domain;

import com.example.iworks.domain.code.entity.Code;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "file")
public class File {

    @Id @GeneratedValue
    @Column(name = "file_id", nullable = false)
    private int fileId; //파일 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_owner_category_code_id", referencedColumnName = "code_id", nullable = false)
    private Code fileCategoryCode; //파일 카테고리

    @Column(name = "file_owner_id", nullable = false)
    private int fileOwnerId; //파일 주체 아이디

    @Column(name = "file_uploader_id", nullable = false)
    private int fileUploaderId; //파일 등록자 아이디

    @Column(name = "file_original_name")
    private String fileOriginalName; //원본 파일명

    @Column(name = "file_save_name")
    private String fileSaveName; //저장 파일명

    @Column(name = "file_size")
    private long fileSize; //파일 사이즈

    @Column(name = "file_type")
    private String fileType; //파일 타입

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "file_created_at")
    private LocalDateTime fileCreatedAt; //파일 등록 일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "file_deleted_at")
    private LocalDateTime fileDeletedAt; //파일 삭제 일시

    @Column(name = "file_is_deleted", columnDefinition = "boolean default false")
    private Boolean fileIsDeleted; //파일 삭제 여부

    public void delete() {
        this.fileDeletedAt = LocalDateTime.now();
        this.fileIsDeleted = true;
    }

}
