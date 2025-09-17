package org.example.coding_convention.likes.service;

import lombok.RequiredArgsConstructor;
import org.example.coding_convention.likes.model.Like;
import org.example.coding_convention.likes.repository.LikeRepository;
import org.example.coding_convention.project.model.Project;
import org.example.coding_convention.project.repository.ProjectRepository;
import org.example.coding_convention.user.model.User;
import org.example.coding_convention.user.model.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public void likes(UserDto.AuthUser authUser, Integer projectIdx) {
        User user = User.builder().idx(authUser.getIdx()).build();
        Project project = projectRepository.findById(projectIdx)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트 없음"));

        Optional<Like> result = likeRepository.findByUserAndProject(user, project);

        if (result.isPresent()) {
            likeRepository.delete(result.get());
            project.decreaseLikeCount();
        } else {
            likeRepository.save(Like.builder().user(user).project(project).build());
            project.increaseLikeCount();
        }
    }
}
