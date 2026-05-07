package com.panscience.assingment.repository;

import com.panscience.assingment.model.Chunk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChunkRepository extends JpaRepository<Chunk, Long> {

    List<Chunk> findByFileId(Long fileId);
}