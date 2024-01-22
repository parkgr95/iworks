package com.example.iworks.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "file")
public class File {

    @Id @GeneratedValue
    @Column(name = "file_id", nullable = false)
    private int file_id; //파일 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_category_code_id", referencedColumnName = "code_id", nullable = false)
    private Code fileCategoryCode; //파일 카테고리 코드 아이디

    @Column(name = "file_owner_id", nullable = false)
    private int fileOwnerId; // 파일 주체 아이디

    @Column(name = "file_name")
    private String fileName; //파일명

    @Column(name = "file_path")
    private String filePath; //파일 경로

    @Column(name = "file_type")
    private String fileType; // 파일 타입 .jpg, .pdf

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "file_created_at")
    private LocalDateTime fileCreatedAt;

    @Column(name = "file_uploader_id", nullable = false)
    private int fileUploaderId; //파일 등록자 아이디

}
