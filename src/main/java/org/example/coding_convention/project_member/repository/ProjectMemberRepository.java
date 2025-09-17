package org.example.coding_convention.project_member.repository;

import org.example.coding_convention.project_member.model.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Integer> {
    List<ProjectMember> findByUser_Idx(Integer userId);
    List<ProjectMember> findByProject_Idx(Integer projectId);

    @Query("SELECT p FROM ProjectMember p JOIN FETCH p.project JOIN FETCH p.project.user WHERE p.user.idx = :idx")
    List<ProjectMember> findByProjectList(Integer idx);
}
