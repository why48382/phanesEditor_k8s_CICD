-- ===============================
-- 데이터베이스 선택
-- ===============================
-- 데이터베이스가 존재하면 삭제
DROP
DATABASE IF EXISTS project;

-- 데이터베이스 생성
CREATE
DATABASE project
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

-- 데이터베이스 사용
USE
project;

-- ===============================
-- 기존 테이블 삭제 (외래키 역순)
-- ===============================
DROP TABLE IF EXISTS email_verify;
DROP TABLE IF EXISTS project_member;
DROP TABLE IF EXISTS files;
DROP TABLE IF EXISTS chat;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS likes;

-- ===============================
-- users 테이블 생성
-- ===============================
CREATE TABLE users
(
    idx             INT AUTO_INCREMENT PRIMARY KEY,
    email           VARCHAR(120) NOT NULL UNIQUE,
    nickname        VARCHAR(200) NOT NULL,
    password        VARCHAR(100),
    profile_img     VARCHAR(500),
    nick_updated_at DATETIME,
    created_at      DATETIME     NOT NULL,
    updated_at      DATETIME     NOT NULL,
    platform        VARCHAR(20)  NOT NULL,
    platform_key    VARCHAR(200),
    status          VARCHAR(20)  NOT NULL,
    last_login      DATETIME,
    browser         VARCHAR(100),
    enabled         BOOLEAN
);

-- ===============================
-- project 테이블 생성
-- ===============================
CREATE TABLE project
(
    idx          INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(255),
    url          VARCHAR(255),
    description  TEXT,
    language     VARCHAR(20),
    creator_id   INT,
    like_count   INT NOT NULL DEFAULT 0,
    FOREIGN KEY (creator_id) REFERENCES users (idx) ON DELETE CASCADE
);

-- ===============================
-- project 테이블 생성
-- ===============================
CREATE TABLE likes
(
    idx int AUTO_INCREMENT PRIMARY KEY,
    user_idx int,
    project_idx int,
    FOREIGN KEY (user_idx) REFERENCES users (idx) ON DELETE CASCADE,
    FOREIGN KEY (project_idx) REFERENCES project (idx) ON DELETE CASCADE
);

-- ===============================
-- files 테이블 생성
-- ===============================
CREATE TABLE files
(
    idx          INT AUTO_INCREMENT PRIMARY KEY,
    file_name    VARCHAR(80),
    file_path    VARCHAR(500),
    created_at   DATETIME    NOT NULL,
    type         VARCHAR(20) NOT NULL,
    save_time_at DATETIME    NOT NULL,
    project_idx  INT,
    url          VARCHAR(200),
    UNIQUE KEY `uk_file_name_file_path` (file_name, file_path),
    FOREIGN KEY (project_idx) REFERENCES project (idx) ON DELETE CASCADE
);

-- ===============================
-- project_member 테이블 생성
-- ===============================
CREATE TABLE project_member
(
    idx        INT AUTO_INCREMENT PRIMARY KEY,
    status     VARCHAR(20),
    user_id    INT,
    project_id INT,
    FOREIGN KEY (user_id) REFERENCES users (idx) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES project (idx) ON DELETE CASCADE
);

-- ===============================
-- email_verify 테이블 생성
-- ===============================
CREATE TABLE email_verify
(
    idx      INT AUTO_INCREMENT PRIMARY KEY,
    uuid     VARCHAR(255),
    user_idx INT,
    FOREIGN KEY (user_idx) REFERENCES users (idx) ON DELETE CASCADE
);

-- ===============================
-- chat 테이블 생성
-- ===============================
CREATE TABLE chats
(
    idx        INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT,
    user_id    INT,
    message    TEXT,
    sent_at    DATETIME NOT NULL,
    FOREIGN KEY (project_id) REFERENCES project (idx) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (idx) ON DELETE CASCADE
);


-- ===============================
-- 샘플 데이터 삽입
-- ===============================

-- users (기존 데이터 유지)
INSERT INTO users (email, nickname, password, created_at, updated_at, platform, status, enabled)
VALUES ('admin@example.com', '관리자', '$2a$10$FWWMVBtbwDoYlxAdue1rV.pf8EKxbmQ.E9fj1.pQfkmfmddSzZe6m', NOW(), NOW(), 'LOCAL', 'ACTIVE', true),
       ('user1@example.com', '유저1', '$2a$10$FWWMVBtbwDoYlxAdue1rV.pf8EKxbmQ.E9fj1.pQfkmfmddSzZe6m', NOW(), NOW(), 'LOCAL', 'ACTIVE', true),
       ('user2@example.com', '유저2', '$2a$10$FWWMVBtbwDoYlxAdue1rV.pf8EKxbmQ.E9fj1.pQfkmfmddSzZe6m', NOW(), NOW(), 'LOCAL', 'ACTIVE', true),
       ('user3@example.com', '유저3', '$2a$10$FWWMVBtbwDoYlxAdue1rV.pf8EKxbmQ.E9fj1.pQfkmfmddSzZe6m', NOW(), NOW(), 'GOOGLE', 'ACTIVE', true),
       ('user4@example.com', '유저4', '$2a$10$FWWMVBtbwDoYlxAdue1rV.pf8EKxbmQ.E9fj1.pQfkmfmddSzZe6m', NOW(), NOW(), 'KAKAO', 'INACTIVE', true),
       ('wnstjs031@naver.com', '유저5', '$2a$10$FWWMVBtbwDoYlxAdue1rV.pf8EKxbmQ.E9fj1.pQfkmfmddSzZe6m', NOW(), NOW(), 'LOCAL', 'INACTIVE', true);

-- project (총 3개 프로젝트 생성)
INSERT INTO project (project_name, url, description, language, creator_id, like_count)
VALUES ('프로젝트 A - 웹사이트', 'http://project.a.com', '반응형 웹사이트 제작', 'JAVASCRIPT', 1, 3),
       ('프로젝트 B - AI 챗봇', 'http://project.b.com', '자연어 처리 기반 챗봇 개발', 'PYTHON', 2, 2),
       ('프로젝트 C - 모바일 앱', 'http://project.c.com', '안드로이드/iOS 앱 개발', 'JAVA', 1, 2),
       ('프로젝트 D - 쇼핑몰 플랫폼', 'http://project.d.com', '전자상거래 플랫폼 개발', 'JAVA', 3, 2),
       ('프로젝트 E - 날씨 예보 시스템', 'http://project.e.com', '실시간 기상 데이터 분석 및 예보', 'PYTHON', 2, 1),
       ('프로젝트 F - 포트폴리오 웹', 'http://project.f.com', '개인 포트폴리오 웹사이트 제작', 'JAVASCRIPT', 4, 2),
       ('프로젝트 G - 금융 데이터 분석', 'http://project.g.com', '빅데이터 기반 금융 리포트 생성', 'PYTHON', 5, 2),
       ('프로젝트 H - 헬스케어 앱', 'http://project.h.com', '운동 및 건강 관리 앱 개발', 'PYTHON', 1, 2),
       ('프로젝트 I - 온라인 교육 플랫폼', 'http://project.i.com', '화상 강의 및 퀴즈 시스템', 'JAVA', 2, 2),
       ('프로젝트 J - 여행 추천 서비스', 'http://project.j.com', '사용자 맞춤 여행 코스 추천', 'PYTHON', 3, 2),
       ('프로젝트 K - 음악 스트리밍 서비스', 'http://project.k.com', '실시간 음악 스트리밍 구현', 'PYTHON', 4, 2),
       ('프로젝트 L - 블로그 CMS', 'http://project.l.com', '사용자 친화적 콘텐츠 관리 시스템', 'PYTHON', 2, 2),
       ('프로젝트 M - 채팅 애플리케이션', 'http://project.m.com', '실시간 그룹 채팅 및 파일 전송', 'JAVASCRIPT', 1, 2),
       ('프로젝트 N - 주식 트레이딩 봇', 'http://project.n.com', '자동 주식 매매 알고리즘 개발', 'PYTHON', 5, 2),
       ('프로젝트 O - 이미지 인식 AI', 'http://project.o.com', '딥러닝 기반 이미지 분류 시스템', 'PYTHON', 3, 2),
       ('프로젝트 P - 스마트홈 IoT', 'http://project.p.com', 'IoT 기기 제어 및 자동화', 'JAVASCRIPT', 2, 2),
       ('프로젝트 Q - 뉴스 크롤러', 'http://project.q.com', '웹 크롤링 기반 뉴스 요약 시스템', 'PYTHON', 4, 2),
       ('프로젝트 R - 소셜 네트워크', 'http://project.r.com', '팔로우/게시물 공유 소셜 서비스', 'JAVA', 1, 2),
       ('프로젝트 S - 추천 알고리즘', 'http://project.s.com', '사용자 행동 기반 추천 엔진', 'PYTHON', 3, 2),
       ('프로젝트 T - 온라인 설문조사', 'http://project.t.com', '실시간 설문 및 통계 분석', 'PYTHON', 2, 2),
       ('프로젝트 U - ERP 시스템', 'http://project.u.com', '기업 자원 관리 시스템 개발', 'JAVASCRIPT', 5, 2),
       ('프로젝트 V - 스마트 미러', 'http://project.v.com', '음성 인식 기반 스마트 미러 개발', 'PYTHON', 4, 2),
       ('프로젝트 W - 증강현실 앱', 'http://project.w.com', 'AR 기술을 활용한 모바일 앱', 'PYTHON', 1, 2),
       ('프로젝트 X - 전자투표 시스템', 'http://project.x.com', '블록체인 기반 전자 투표 플랫폼', 'JAVA', 2, 2),
       ('프로젝트 Y - 번역 애플리케이션', 'http://project.y.com', '실시간 다국어 번역 기능 구현', 'PYTHON', 3, 2),
       ('프로젝트 Z - 스포츠 분석 플랫폼', 'http://project.z.com', '선수 경기 데이터 분석 서비스', 'PYTHON', 4, 2),
       ('프로젝트 AA - 게임 서버', 'http://project.aa.com', '멀티플레이어 온라인 게임 서버 구축', 'JAVASCRIPT', 5, 2),
       ('프로젝트 AB - 영상 스트리밍 서비스', 'http://project.ab.com', '고화질 영상 스트리밍 플랫폼', 'PYTHON', 1, 0);  -- AB는 likes 데이터 없음



-- like
INSERT INTO `likes` (user_idx, project_idx) VALUES
                                               (1, 1),
                                               (2, 1),
                                               (3, 1),
                                               (4, 2),
                                               (5, 2),
                                               (1, 3),
                                               (2, 3),
                                               (3, 4),
                                               (4, 4),
                                               (5, 5),
                                               (1, 6),
                                               (2, 6),
                                               (3, 7),
                                               (4, 7),
                                               (5, 8),
                                               (1, 8),
                                               (2, 9),
                                               (3, 9),
                                               (4, 10),
                                               (5, 10),
                                               (1, 11),
                                               (2, 11),
                                               (3, 12),
                                               (4, 12),
                                               (5, 13),
                                               (1, 13),
                                               (2, 14),
                                               (3, 14),
                                               (4, 15),
                                               (5, 15),
                                               (1, 16),
                                               (2, 16),
                                               (3, 17),
                                               (4, 17),
                                               (5, 18),
                                               (1, 18),
                                               (2, 19),
                                               (3, 19),
                                               (4, 20),
                                               (5, 20),
                                               (1, 21),
                                               (2, 22),
                                               (3, 23),
                                               (4, 24),
                                               (5, 25),
                                               (1, 25),
                                               (2, 24),
                                               (3, 23),
                                               (4, 22),
                                               (5, 21);


-- files
INSERT INTO files (file_name, file_path, URL, created_at, type, save_time_at, project_idx)
VALUES
    -- 루트 디렉토리의 프로젝트 폴더들 (URL: NULL)
    ('project_a', '/', NULL, NOW(), 'DIRECTORY', NOW(), 1),
    ('project_b', '/', NULL, NOW(), 'DIRECTORY', NOW(), 2),
    ('project_c', '/', NULL, NOW(), 'DIRECTORY', NOW(), 3),

    -- 프로젝트 A의 폴더들 (URL: NULL)
    ('assets', '/project_a/', NULL, NOW(), 'DIRECTORY', NOW(), 1),
    ('src', '/project_a/', NULL, NOW(), 'DIRECTORY', NOW(), 1),
    ('components', '/project_a/src/', NULL, NOW(), 'DIRECTORY', NOW(), 1),
    ('buttons', '/project_a/src/components/', NULL, NOW(), 'DIRECTORY', NOW(), 1),

    -- 프로젝트 B의 폴더들 (URL: NULL)
    ('data', '/project_b/', NULL, NOW(), 'DIRECTORY', NOW(), 2),
    ('model', '/project_b/', NULL, NOW(), 'DIRECTORY', NOW(), 2),

    -- 프로젝트 C의 폴더들 (URL: NULL)
    ('src', '/project_c/', NULL, NOW(), 'DIRECTORY', NOW(), 3),
    ('res', '/project_c/', NULL, NOW(), 'DIRECTORY', NOW(), 3),
    ('values', '/project_c/res/', NULL, NOW(), 'DIRECTORY', NOW(), 3),
    ('layout', '/project_c/res/', NULL, NOW(), 'DIRECTORY', NOW(), 3),

    -- 프로젝트 A의 파일들 (URL: NULL)
    ('index.js', '/project_a/', NULL, NOW(), 'FILE', NOW(), 1),
    ('style.css', '/project_a/', NULL, NOW(), 'FILE', NOW(), 1),
    ('README.md', '/project_a/', NULL, NOW(), 'FILE', NOW(), 1),
    ('logo.png', '/project_a/assets/', NULL, NOW(), 'FILE', NOW(), 1),

    -- 프로젝트 B의 파일들 (URL: NULL)
    ('main.py', '/project_b/', NULL, NOW(), 'FILE', NOW(), 2),
    ('data.json', '/project_b/data/', NULL, NOW(), 'FILE', NOW(), 2),
    ('model.h5', '/project_b/model/', NULL, NOW(), 'FILE', NOW(), 2),
    ('requirements.txt', '/project_b/', NULL, NOW(), 'FILE', NOW(), 2),

    -- 프로젝트 C의 파일들 (URL: NULL)
    ('MainActivity.java', '/project_c/src/', NULL, NOW(), 'FILE', NOW(), 3),
    ('strings.xml', '/project_c/res/values/', NULL, NOW(), 'FILE', NOW(), 3),
    ('activity_main.xml', '/project_c/res/layout/', NULL, NOW(), 'FILE', NOW(), 3),
    ('AndroidManifest.xml', '/project_c/src/', NULL, NOW(), 'FILE', NOW(), 3),

    -- 프로젝트 A에 추가할 S3 파일들 (file_path, URL 포함)
    ('one.txt',
     '/project_a/20250819/60da4bc5-c56c-4eab-b3a9-c23e0f127b2b_one.txt',
     'https://aws-junsun-s3.s3.amazonaws.com/20250819/60da4bc5-c56c-4eab-b3a9-c23e0f127b2b_one.txt',
     NOW(), 'FILE', NOW(), 1),
    ('two.txt',
     '/project_a/20250819/6af60a02-5763-4abb-a873-1974484fc007_two.txt',
     'https://aws-junsun-s3.s3.amazonaws.com/20250819/6af60a02-5763-4abb-a873-1974484fc007_two.txt',
     NOW(), 'FILE', NOW(), 1),
    ('third.txt',
     '/project_a/20250819/5333a967-ef32-4d0f-82fb-d8337e053141_third.txt',
     'https://aws-junsun-s3.s3.amazonaws.com/20250819/5333a967-ef32-4d0f-82fb-d8337e053141_third.txt',
     NOW(), 'FILE', NOW(), 1),
    ('four.txt',
     '/project_a/20250819/159da373-89cc-4b21-a843-6ba427d161f4_four.txt',
     'https://aws-junsun-s3.s3.amazonaws.com/20250819/159da373-89cc-4b21-a843-6ba427d161f4_four.txt',
     NOW(), 'FILE', NOW(), 1),
    ('five.txt',
     '/project_a/20250819/021ab366-3155-4820-8311-a5ce07afc116_five.txt',
     'https://aws-junsun-s3.s3.amazonaws.com/20250819/021ab366-3155-4820-8311-a5ce07afc116_five.txt',
     NOW(), 'FILE', NOW(), 1);

-- project_member (각 프로젝트에 멤버 추가)
INSERT INTO project_member (status, user_id, project_id)
VALUES ('ADMIN', 1, 1), -- 관리자가 프로젝트 A 멤버 (생성자)
       ('USER', 2, 1),  -- 유저1이 프로젝트 A 멤버

       ('ADMIN', 2, 2), -- 유저1이 프로젝트 B 멤버 (생성자)
       ('USER', 1, 2),  -- 관리자가 프로젝트 B 멤버

       ('ADMIN', 1, 3), -- 관리자가 프로젝트 C 멤버 (생성자)
       ('USER', 2, 3);
-- 유저1이 프로젝트 C 멤버

-- chat (각 프로젝트에 여러 개의 메시지 추가)
INSERT INTO chats (project_id, user_id, message, sent_at)
VALUES
    -- 프로젝트 A 채팅
    (1, 1, '웹사이트 랜딩 페이지 디자인 초안을 공유했습니다.', NOW()),
    (1, 2, '확인했습니다! 피드백 드릴게요. 이미지 파일은 assets 폴더에 넣으면 되겠죠?', NOW()),
    (1, 1, '네, 맞아요. 로고 파일도 같이 업로드했습니다.', NOW()),
    (1, 2, '좋습니다. css 수정해서 레이아웃 잡아볼게요.', NOW()),

    -- 프로젝트 B 채팅
    (2, 2, '챗봇 학습 데이터셋을 업데이트했습니다.', NOW()),
    (2, 1, '고생 많으셨습니다. 모델 재학습 돌려보겠습니다.', NOW()),
    (2, 2, '모델 성능 개선을 위해 데이터 전처리 스크립트를 수정해볼까요?', NOW()),
    (2, 1, '좋은 생각입니다. requirements.txt에 필요한 라이브러리 추가해주세요.', NOW()),

    -- 프로젝트 C 채팅
    (3, 1, '앱 기능 명세서 최종본입니다.', NOW()),
    (3, 2, '새로운 푸시 알림 기능에 대해 논의가 필요할 것 같아요.', NOW()),
    (3, 1, '네, 좋아요. 어떤 부분에 대해 논의하면 좋을까요?', NOW()),
    (3, 2, 'AndroidManifest.xml 설정에 대해서 얘기해보면 좋겠습니다.', NOW());

-- email_verify (기존 데이터 유지)
INSERT INTO email_verify (uuid, user_idx)
VALUES ('abc123', 1),
       ('xyz456', 2);
