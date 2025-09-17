package org.example.coding_convention.project;

import org.example.coding_convention.CodingConventionApplication;
import org.example.coding_convention.project.model.ProjectDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CodingConventionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @DisplayName("ProjectController 테스트 프로젝트 생성 테스트")
    @Test
    @Order(1)
    public void testPostRegister() {
        ProjectDto.ProjectReq dto = ProjectDto.ProjectReq.builder()
                .projectName("프로젝트01")
                .description("프로젝트 설명01")
                .language("JAVA")
                .userId(1)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProjectDto.ProjectReq> httpEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<ProjectDto.ProjectRes> resp =
                testRestTemplate.exchange("/product/register", HttpMethod.POST, httpEntity, ProjectDto.ProjectRes.class);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
        assertEquals(1,resp.getBody().getIdx());
    }

    @DisplayName("ProjectController 테스트 상세 조회 테스트")
    @Test
    @Order(2)
    public void testGetRead() {
        ResponseEntity<ProjectDto.ProjectRead> resp =
                testRestTemplate.exchange("/project/read/1", HttpMethod.POST, null, ProjectDto.ProjectRead.class);
//        assertEquals(HttpStatus.OK, resp.getStatusCode());
//        assertNotNull(resp.getBody());
//        assertEquals("프로젝트01", resp.getBody().getProjectName());

    }
}
