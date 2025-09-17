package org.example.coding_convention.project.repository;

import org.example.coding_convention.project.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
//    SELECT * FROM project LEFT JOIN project.users ON project.creator_id = users.idx WHERE users.idx = 1;
    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.user u WHERE u.idx = :userIdx")
    List<Project> findByUser_Idx(Integer userIdx);

    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.fileList WHERE p.idx = :idx")
    Optional<Project> findByProjectIdx(Integer idx);

    Page<Project> findByProjectNameContaining(String keyword, Pageable pageable);
}
