package com.panscience.assingment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.panscience.assingment.model.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {}
