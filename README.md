# 📖 개인 노트 서비스 README
<br>

## 프로젝트 상세 페이지 
- https://elastic-caraway-da9.notion.site/Personal_Note_Service-aa5fde85462d48638da8db124fdbb0c9

<br>

## 프로젝트 소개

- 개인 노트 서비스는 유저와 관리자를 구분하여 유저는 자신의 노트를 작성하고 관리자는 유저의 노트를 관리 및 공지사항을 게시할 수 서비스입니다.
- 유저는 노트를 작성하고 수정할 수 있으며, 관리자는 유저의 노트를 관리하고 공지사항을 게시할 수 있습니다.
- 다른 유저의 개인 노트는 볼 수 없으며, 관리자는 유저의 노트를 수정할 수 없습니다.
- 관리자는 공지사항을 작성 할 수 있고 일반 유저들은 이 공지사항을 볼 수 있습니다.
- 유저와 관리자 모두 로그인, 회원가입, 로그아웃, 로그인 유지하기 등의 기능을 사용할 수 있습니다.

<br>

## 문제점 인식
- 이전에 진행했던 대학교 동아리 플랫폼 프로젝트에서 로그인을 세션 인증 방식으로 구현 및 회원가입 과정에서 사용자가 기입한 정보를 데이터베이스에 아이디와 비밀번호를 평문으로 저장하는 방식에 대해 보안 위험 인식
- Spring Security의 비밀번호 암호화와 JWT Token을 활용한 토큰 인증 방식으로 기존 문제점을 개선 할 수 있는 개인 노트 서비스 개발 프로젝트 기획
<br>

## 주요 기능

- passwordEncorder를 활용하여 비밀번호 암호화 및 암호화 된 정보 저장
- JWT Token을 활용하여 토큰 인증 방식으로 로그인, 회원가입, 로그인 유지하기 기능 구현
- 관리자, 사용자를 구분하여 인증 및 인가 구현, 권한 별 접근 및 사용 기능 구분
- 관리자 - 공지사항 작성 및 게시물 관리 기능
- 사용자 - 개인 노트 서비스

<br>

## 트러블 슈팅

- java: variable em might not have been initialized
  - user/UserService 구현중 발생
  - UserRepository 의존성 주입을 위해 final로 선언했을 때 에러 발생
  - 해결 : @RequiredArgsConstructor를 사용하여 final 필드를 파라미터로 받는 생성자를 생성해서 해결
- login 페이지 뷰 반환이 안되는 오류 발생
  - 400 에러 발생
  - 해결 : @Configuration으로 등록 해서 해결

<br>

## 프로젝트 후기

- 배운점
    - 이 프로젝트를 통해 보안 강화를 위한 다양한 Spring Security 설정과 JWT 토큰의 안전한 관리 방법에 대해 배울 수 있었습니다. 특히, CSRF 방어, 비밀번호 암호화, 역할 기반 접근 제어 등 보안 관련 실무 지식을 넓힐 수 있었던 것이 큰 수확이었습니다.
    - 또한, Spring Security와 JWT 토큰을 사용함으로써, RESTful 서비스에 적합한 인증 방식을 구현하는 방법에 대해 배울 수 있었습니다.
    - 대학교 동아리 플랫폼 프로젝트를 진행하고 개선하고 싶었던 사용자 정보 저장 방식을 Spring Security의 passwordEncorder를 통해 암호화된 비밀번호로 데이터베이스 저장 방식으로 개선
    - 세션 하이재킹, CSRF 등의 세션 인증 방식의 보안 취약점을 탐구하고, 토큰 인증 방식으로의 개선을 통해 데이터 무결성 보장 및 공격 방지가 가능하여 기존 세션 인증 방식의 단점 개선
    - 프로젝트를 하며 부족한 부분을 개선 하기위해 방안을 찾아서 탐구하고 습득한 기술을 프로젝트에 활용하여 향상한 경험
    - 개인 정보 보안의 중요성과 공격을 방지 할 수 있는 방법을 탐구하여 보안 지식 함양

<br>

## 1. 개발 기술 스택

- Front : Bootstrap
- Back-end : Java, Spring WebMVC, Spring Security, Thymeleaf, Lombok, Spring Jpa 
- Database : H2 database
- Build : Gradle
- 형상관리 : Github
<br>

## 2. 개발 사용 기술

### Spring Security

- 인증(Authentication)과 권한 부여(Authorization)
    - SpringSecurityConfig 클래스를 통해 관리자 페이지 접근 권한을 설정하고, 사용자 인증 정보를 관리합니다.
    - 특정 역할(예: 관리자)이 있는 사용자만 특정 경로(/admin)에 접근할 수 있도록 구성됩니다.
- 비밀번호 암호화
    - PasswordEncoderConfig 클래스에서 PasswordEncoder를 빈으로 등록함으로써, 사용자 비밀번호의 안전한 저장과 검증을 위한 암호화 메커니즘을 제공합니다.

- 사용자 인증 커스터마이징
  - InitializeDefaultConfig 클래스를 통해 기본 사용자와 관리자를 시스템에 등록합니다.
  - MvcConfig 클래스를 통해 사용자 정의 로그인 페이지(/login)를 설정합니다.
    
### JWT Token

- JWT 토큰 생성 및 검증
    - JwtUtils 클래스에서는 JWT 토큰의 생성 및 검증 로직을 구현
    -  사용자가 로그인에 성공하면 JWT 토큰을 생성하여 반환하고, 토큰을 포함한 요청이 들어올 때마다 해당 토큰의 유효성을 검증합니다.
    - 기존 세션 인증에서 JWT 토큰 인증으로 변경함으로써, 서버의 세션 저장소를 사용하지 않아도 되고, 서버의 확장성을 높일 수 있습니다.
- 인증 및 인가 필터
    - JwtAuthenticationFilter와 JwtAuthorizationFilter 클래스를 통해 인증 및 인가 처리를 진행합니다.
    - JwtAuthenticationFilter는 사용자의 로그인 요청을 처리하고 JWT 토큰을 생성하여 반환합니다.
    - JwtAuthorizationFilter는 요청에 포함된 JWT 토큰을 검증하여 사용자의 요청이 유효한지 확인합니다.
- 사용자 인증 정보 관리
  - 토큰 자체가 사용자의 인증 정보를 포함하므로, 상태를 유지할 필요가 없어 RESTful 서비스에 적합한 인증 방식을 제공합니다.
  - JWT 토큰을 사용함으로써, 서버 측에서 별도로 사용자 세션을 관리할 필요가 없어집니다.
<br>


## 3. 페이지별 기능

### [초기화면]
- 서비스 접속 초기화면

<img width="1925" alt="홈화면" src="https://github.com/BellOne4222/Spring-security-Personal_Note_Service/assets/127610150/7063f693-e64f-4139-83eb-c316e6db9fca">
<br>

### [로그인]
- 로그인 페이지
    - 로그인인 및 로그인 유지하기 기능 구현

  <img width="1943" alt="로그인" src="https://github.com/BellOne4222/Spring-security-Personal_Note_Service/assets/127610150/c5294046-01b5-41ae-96da-8c0fd709fab5">
<br>

### [관리자 계정으로 로그인 후]
- 상단 좌측에 admin 계정으로 로그인을 확인 할 수 있도록 구현
  - 네비게이션 바에 관리자로 로그인하면 관리자 페이지로 이동하는 버튼이 나타납니다.
  <img width="1921" alt="관리자 로긍니" src="https://github.com/BellOne4222/Spring-security-Personal_Note_Service/assets/127610150/b4ca1d5e-0ce6-48c5-bb45-9ea7967a0331">
<br>

### [사용자 계정으로 로그인 후]
- 사용자 계정으로 로그인 후에는 상단 좌측에 사용자 계정으로 로그인을 확인 할 수 있도록 구현
  - 네비게이션 바에 사용자로 로그인하면 개인 노트로 이동하는 버튼이 나타납니다.
- SNS(카카오톡, 구글, 페이스북) 로그인 기능은 구현되어 있지 않습니다.
  <img width="2026" alt="사용자 로그인 화면" src="https://github.com/BellOne4222/Spring-security-Personal_Note_Service/assets/127610150/e7a407ed-f41a-45f8-b63e-0c75c4435898">
<br>

### [관리자 페이지]
- 공지사항 및 게시물 내역을 관리자 페이지에서 확인할 수 있습니다.
- 
  <img width="1925" alt="관리자 페이지" src="https://github.com/BellOne4222/Spring-security-Personal_Note_Service/assets/127610150/6ad4a19b-b496-44ce-9899-012809d238ae">
<br>

### [사용자 개인노트 및 글작성]
- 사용자 계정으로 개인노트 확인 및 글작성을 할 수 있는 페이지
  <img width="1916" alt="유저 개인노트 및 글작성" src="https://github.com/BellOne4222/Spring-security-Personal_Note_Service/assets/127610150/5f6f4434-d825-46a3-a87b-88551577e3d4">
<br>

### [사용자 로그인시 공지사항]
- 사용자 로그인시 공지사항을 확인할 수 있는 페이지
  - 공지사항을 확인만 가능하고 수정은 불가능
    <img width="1924" alt="유저 로그인시 공지사항" src="https://github.com/BellOne4222/Spring-security-Personal_Note_Service/assets/127610150/8068b675-3ef7-4ed6-b813-3c0683209dab">
    <br>

### [관리자 로그인시 공지사항 및 공지사항 작성]
- 관리자 로그인시 공지사항을 확인 및 작성할 수 있는 페이지
  - 공지사항을 확인 및 작성 가능

    <img width="1916" alt="공지사항 및 공지사항 작성" src="https://github.com/BellOne4222/Spring-security-Personal_Note_Service/assets/127610150/6e59a745-d87d-4113-8819-7f4e37f840ec">
    <br>


## 4. 테스트 코드

- mockMVC를 통해 가짜 유저로 테스트 케이스를 생성하여 테스트 코드 작성
- admin
  - 인증 없이 접근하는 경우
  - 어드민 권한으로 접근하는 경우
  - 유저 권한으로 접근하는 경우
- note controller
  - 인증 없이 노트에 접근하는 경우
  - 인증된 사용자가 노트에 접근하는 경우
  - 인증 없이 노트를 작성하는 경우
  - 어드민 권한으로 노트를 작성하는 경우
  - 유저 권한으로 노트를 작성하는 경우
  - 인증 없이 노트를 삭제하는 경우
  - 유저 권한으로 노트를 삭제하는 경우
  - 어드민 권한으로 노트를 삭제하는 경우
- note service
  - 사용자가 게시한 노트를 조회하는 경우
  - 어드민이 모든 사용자의 게시물을 조회하는 경우
  - 노트를 저장하는 경우
  - 노트를 삭제하는 경우
- 공지사항 controller
  - 인증 없이 공지사항을 조회하는 경우
  - 인증된 사용자가 공지사항을 조회하는 경우
  - 인증 없이 공지사항을 게시하는 경우
  - 유저 권한으로 공지사항을 게시하는 경우
  - 어드민 권한으로 공지사항을 게시하는 경우
  - 인증 없이 공지사항을 삭제하는 경우
  - 유저 권한으로 공지사항을 삭제하는 경우
  - 어드민 권한으로 공지사항을 삭제하는 경우
- 공지사항 controller
  - 인증 없이 공지사항을 조회하는 경우
  - 인증된 사용자가 공지사항을 조회하는 경우
  - 인증 없이 공지사항을 게시하는 경우
  - 유저 권한으로 공지사항을 게시하는 경우
  - 어드민 권한으로 공지사항을 게시하는 경우
  - 인증 없이 공지사항을 삭제하는 경우
  - 유저 권한으로 공지사항을 삭제하는 경우
  - 어드민 권한으로 공지사항을 삭제하는 경우
- 회원가입 controller
  - CSRF 토큰을 함께 전송하여 리다이렉트 검사





