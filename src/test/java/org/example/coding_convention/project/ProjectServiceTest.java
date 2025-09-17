package org.example.coding_convention.project;

import org.example.coding_convention.project.model.Project;
import org.example.coding_convention.project.model.ProjectDto;
import org.example.coding_convention.project.repository.ProjectRepository;
import org.example.coding_convention.project.service.ProjectService;
import org.example.coding_convention.user.model.User;
import org.example.coding_convention.user.model.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


// 단위 테스트 : 서비스
// 통합 테스트 : 컨트롤러

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock   // 내가 테스트하려는 객체가 의존하고 있는 객체의 가짜 객체
    private ProjectRepository projectRepository;
    @InjectMocks    // 내가 테스트하려는 객체의 가짜 객체
    private ProjectService projectService;

    @Test
    void ProjectService_create_success() {
        // given
        Project.Language dtoLanguage = Project.projectLanguage("JAVA");

        ProjectDto.ProjectReq request = ProjectDto.ProjectReq.builder()
                .projectName("프로젝트01")
                .description("프로젝트 설명01")
                .language("JAVA")
                .userId(1)
                .build();

        User user = User.builder()
                .idx(1)
                .build();
        Project project = Project.builder()
                .idx(1)
                .projectName("프로젝트01")
                .description("프로젝트 설명01")
                .language(dtoLanguage)
                .user(user)
                .build();
        given(projectRepository.save(any(Project.class))).willReturn(project);

        UserDto.AuthUser authUser = UserDto.AuthUser
                .builder()
                .idx(1)
                .build();

        // when
        ProjectDto.ProjectRes response = projectService.save(request, authUser);

        // then
        assertNotNull(response);
        assertEquals(1, response.getIdx());
    }

    void productService_create_failed() {

    }
}