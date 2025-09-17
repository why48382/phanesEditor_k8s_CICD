package org.example.coding_convention.likes.repository;

import org.example.coding_convention.likes.model.Like;
import org.example.coding_convention.project.model.Project;
import org.example.coding_convention.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    Optional<Like> findByUserAndProject(User user, Project project);
}
