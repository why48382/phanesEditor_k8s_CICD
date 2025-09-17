package org.example.coding_convention.project_member.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.example.coding_convention.project.model.Project;
import org.example.coding_convention.user.model.User;

public class ProjectMemberDto {

    @Getter
    @Builder
    @Schema(description = "프로젝트 맴버 초대")
    public static class ProjectMemberReq {
        @Schema(description = "프로젝트의 id", example = "1")
        private Integer projectId;
        @Schema(description = "초대하고 싶은 유저의 닉네임", example = "닉네임")
        private String nickname;
        @Schema(description = "초대할 유저에게 부여할 권한", example = "USER")
        private String status;
        private Integer userId;

        public ProjectMember toEntity(String status, Integer userId) {
            ProjectMember.Status dtoStatus = ProjectMember.memberStatus(status);
            Project project = Project.builder()
                    .idx(projectId)
                    .build();
            User user = User.builder()
                    .idx(userId)
                    .build();

            return ProjectMember.builder()
                    .project(project)
                    .user(user)
                    .status(dtoStatus)
                    .build();
        }
        public ProjectMember toEntity() {
            Project project = Project.builder()
                    .idx(projectId)
                    .build();
            User user = User.builder()
                    .idx(userId)
                    .build();
            ProjectMember.Status dtoStatus = ProjectMember.memberStatus(status);

            return ProjectMember.builder()
                    .project(project)
                    .user(user)
                    .status(dtoStatus)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class ProjectMemberList {
        private Integer userId;
        private String status;
        private String username;

        public static ProjectMemberList from(ProjectMember entity) {
            return ProjectMemberDto.ProjectMemberList.builder()
                    .userId(entity.getUser().getIdx())
                    .username(entity.getUser().getNickname())
                    .status(entity.getStatus().toString())
                    .build();
        }
    }
}
