# 게시판 포트폴리오

> 안진혁(JinHyeok An) - work_log 포트폴리오

<br />

# 👋 Intro

안녕하세요! ***"포기하지 않는 개발자"*** 안진혁입니다!<br />
과거 국비지원 학원 6개월 수료 후 spring, jsp, jquery, mybatis, mariaDB를<br />
활용하여 게시판을 만들고 아마존 ec2에 배포까지 완료했던 경험이 있습니다.<br />

오랜시간이 흘렀지만 다시 기억을 떠올리면서<br />
aop, interceptor, controlleradvice 등의 개념을 통해<br />
더욱 확장성 있고 안정적인 게시판을 만들어 보았습니다.<br />

<br />

# 📝Projects
2주간 진행했던 프로젝트 입니다.<br />
처음에는 기억을 되살리기위해 기존에 작성했던 코드를 분석하느라 시간이 많이 소모되었지만<br />
점점 전체적인 구조가 익숙해져 속도를 붙이게 되었습니다.<br />

## 1. 🛫 MyLittleTrip

> work_log (단순한 게시판)
>
> - 개발기간 : 2026.04.10~04.24
>
> - Backend : Java11, Spring Framework 5.3.18, MyBatis 3.5.9, Spring Security
> - Frontend : JavaScript(ES6), JQuery, AJAX, Bootstrap 3.4.1, Summernote
> - Database : MariaDB
> - DevOps/Tools : Apache Tomcat 9.0
> 
# 🛠 Spring Legacy MVC Framwork
## XML 기반의 의존성 및 환경 설정
- Servlet-context.xml 및 Root-context.xml을 통한 명확한 빈 생명주기 관리와
- WAS(Tomcat) 환경에서의 서블릿 구동 메커니즘을 이해하고 있습니다. 
## MyBatis (Data Mapper)
- Spring Legacy와 MyBatis를 연동하여 SQL Mapper를 통한 쿼리 제어를 수행했습니다.
- 특히 ResultMap과 동적 쿼리를 활용해 게시판의 복잡한 검색 조건을 최적화 했습니다.
# 🛠 보안 및 파일 처리 시스템
## BCrypt 기반 비밀번호 암호화
- Spring Security의 BCryptPasswordEncoder를 도입하여 단방향 해시 암호화를 구현했습니다.
- 사용자의 비밀번호를 Salting 처리하여 DB 침해시에도 개인정보를 안전하게 보호하도록 설계했습니다.

![image](https://github.com/user-attachments/assets/ef4f6e85-1ef4-42dd-bc0b-ae45c93646ab)
## 첨부파일 관리 및 대용량 처리
- MultipartResolver를 활용한 파일 업로드 시스템을 구축했습니다.
- 서버 내 물리경로 보안을 위해서 UUID 기반의 파일명 치환 정책을 적용했으며,
- 원본 파일명과 저장 파일명을 DB에서 매핑 관리하여 데이터 무결성을 확보했습니다.

![image](https://github.com/user-attachments/assets/55f22859-dc08-4c57-8501-3d386375bf7a)
## Summernote 에디터 & 이미지 바이너리 처리
- Summernote를 이용해 작성 중인 이미지를 실시간으로 서버에 업로드 하고,
- 반환된 경로를 HTML에 삽입하는 비동기 이미지 핸들링 로직을 구현했으며
- 게시물 수정 및 삭제 시 DB의 경로 데이터를 참조하여 서버 내 실제 물리 파일을 동기화 하여
- 삭제 또는 추가 함으로서 불필요한 스토리지 점유를 방지하고 리소스 관리 효율을 높였습니다.
 
![image](https://github.com/user-attachments/assets/e3204ce8-bf8f-4f0f-9ede-64e5ac58a40e)
# 🛠 Spring 프레임워크 기술을 활용한 클린코드와 공통 관심사의 분리
## 1.AOP 도입
### 문제상황
- 각 서비스 메서드의 성능을 파악해야 했으나,
- 모든 메서드에 시작/종료 로그를 찍는 것은 코드 중복과 가독성 저하가 있었습니다.
### 해결방안(AOP도입)
- @Aspect, @Around을 사용하여 비즈니스 로직 전후에 실행 시간을 계산하는 로직을 일괄 적용했습니다.

![image](https://github.com/user-attachments/assets/df564c96-2e55-4195-ac11-e5c5015f04b8)
### 성과
- 비즈니스 로직과 부가 기능을 분리하여 코드 유지보수성이 향상되었습니다.
- 전체 서비스의 병목 지점을 데이터로 파악하기 용이해졌습니다.
## 2.인터셉터 도입
### 문제상황
- 로그인한 유저와 로그인하지 않은 유저를 매번 컨트롤러에서 확인하니
- 코드의 중복이 너무 많아지는 문제가 있었습니다.
### 해결방안(인터셉터 도입)
- HandlerInterceptor를 implements 하여 preHandle를 통해 원하는 경로에 검증로직을 일괄 적용했습니다.

![image](https://github.com/user-attachments/assets/d09e46a9-35c7-4ff7-94f7-1670d9b35a49)
### 성과
- 컨트롤러는 더이상 권한을 일일이 확인하지 않을 수 있게 되었으며
- 공통 보안 정책을 한곳에서 관리할 수 있게 되었습니다.
## 3.@ControllerAdvice 도입
### 문제상황
- 모든 컨트롤러의 각 메서드 마다 try-catch블록이 들어가면서 코드의 중복이 심해졌습니다.
- 예측하지 못한 런타임 에러가 발생했을 경우 서버의 스택 트레이스가 그대로 노출되는 문제점이 있었습니다.
### 해결방안(@ControllerAdvice 도입)
- 어플리케이션 내의 모든 컨트롤러에서 발생하는 예외를 한곳으로 모으는 전역 예외처리기(GlobalExceptionHandler)를 구축했습니다.
- @ExceptionHandler를 활용하여 발생한 예외의 종류에 따라 적절한 메서드가 실행되도록 매핑했습니다.

![image](https://github.com/user-attachments/assets/5808ffcd-01c6-4b42-94a3-687dca6dc7e7)
### 성과
- 컨트롤러에서 try-catch를 제거하여 코드가 간결해졌으며 핵심 로직의 가독성이 크게 향상되었습니다.
- 에러 발생 지점과 처리 지점을 분리함으로써 새로운 예외 유형이 추가되어도 공통 클래스 한 곳만 수정하면 되는 구조를 갖추었습니다.

# 📝핵심기능 - 회원가입/로그인
![image](https://github.com/user-attachments/assets/dd8b8d8b-5080-4f39-b8c8-ac79e980dfb2)
# 📝핵심기능 - 게시글 작성
![image](https://github.com/user-attachments/assets/5111cc90-122d-448b-8277-68ef0b0c2276)
# 📝핵심기능 - 검색과 페이징
![image](https://github.com/user-attachments/assets/3813a4fe-2e24-457c-b3a4-6fbbe5def0a7)
- Criteria를 활용하여 페이징을 구현했습니다.

![image](https://github.com/user-attachments/assets/ecc8f7ca-45f3-48aa-a7dc-1fe51b6738a7) 
![image](https://github.com/user-attachments/assets/a4195065-e3e5-4431-9a84-ac1e76f3f1d7)
# 📝핵심기능 - 댓글 작성
![image](https://github.com/user-attachments/assets/04065f0b-185d-4297-b7c3-cded98d228b0)
- REST API를 활용하여 댓글 기능을 설계했습니다.
## 📋 Reply API 명세
[ Base URL: /reply ]
### 1. 댓글 등록
- Method: POST
- Endpoint: /new
- Payload: ReplyVO (JSON)
- Response: "success"
### 2. 댓글 목록 조회
- Method: GET
- Endpoint: /list/{bno}
- Path Variable: bno (게시글 번호)
- Response: List<ReplyDetailDTO>
### 3. 댓글 수정
- Method: PUT
- Endpoint: /edit/{rno}
- Path Variable: rno (댓글 번호)
- Response: "success"
### 4. 댓글 삭제
- Method: DELETE
- Endpoint: /delete/{bno}/{rno}
- Path Variable: bno, rno
- Response: "success"
  
![image](https://github.com/user-attachments/assets/73535f5a-092c-430d-afd9-3e4089460608)
![image](https://github.com/user-attachments/assets/6928e93b-27ab-4c4e-a8f8-0ceb3083b920)

# 📝핵심기능 - 마이페이지(일반유저)
![image](https://github.com/user-attachments/assets/6805ae83-7446-4ed1-9780-d165dbd46dd8)
# 📝핵심기능 - 마이페이지(관리자)
![image](https://github.com/user-attachments/assets/455c8bda-1c10-49e1-bbf4-83456355ec13)


## 감사합니다.

# 📞 Contact
- 이메일 : million_food@naver.com
- 연락처 : 010-3804-1915
