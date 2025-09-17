package org.example.coding_convention.project.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.valves.rewrite.InternalRewriteMap;
import org.example.coding_convention.project.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.coding_convention.project.model.QProject.project;
import static org.example.coding_convention.user.model.QUser.user;

@Repository
@RequiredArgsConstructor
public class ProjectQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Page<Project> searchProjects(String projectName, String email, String language, Pageable pageable) {
        List<Project> content = queryFactory
                .selectFrom(project)
                .join(project.user, user).fetchJoin() // User join
                .where(
                        projectNameContains(projectName),
                        emailContains(email),
                        languageEq(language)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(project.idx.desc())
                .fetch();

        long total = queryFactory
                .selectFrom(project)
                .join(project.user, user)
                .where(
                        projectNameContains(projectName),
                        emailContains(email),
                        languageEq(language)
                )
                .fetchCount();

        return new PageImpl<>(content, pageable, total);


    }


    private boolean hasText(String str) {
        return str != null && !str.isBlank();
    }

    private BooleanExpression projectNameContains(String projectName) {
        return hasText(projectName) ? project.projectName.containsIgnoreCase(projectName) : null;
    }

    private BooleanExpression languageEq(String language) {
        return hasText(language) ? project.language.eq(Project.Language.valueOf(language.toUpperCase())) : null;
    }

    private BooleanExpression emailContains(String email) {
        return hasText(email) ? project.user.email.containsIgnoreCase(email) : null;
    }
}
