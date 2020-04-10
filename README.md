# Episode 프레임워크

## 기본 구성

- Spring Boot 2.1.6 기반
- Gradle build tool 사용
- .properties 파일 대신 .yml 파일로 설정
- Template Engine 및 Jsp 사용하지 않음 
- Single Page Application으로 구성
- Frontend 개발은 Vue 2 기반
- jQuery는 가급적으로 사용하지 않음
- Security는 JWT를 이용한 token 방식으로 구현

### 기본으로 추가하는 라이브러리

```
    implementation 'org.springframework.boot:spring-boot-starter-jdbc'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-aop'
    implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.0.0'
    annotationProcessor "org.springframework.boot:spring-boot-configuration-processor"
    annotationProcessor 'org.projectlombok:lombok'
    compileOnly 'org.projectlombok:lombok'
    runtimeOnly 'org.springframework.boot:spring-boot-devtools'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
```

### 프로젝트 생성 후 추가한 라이브러리

```
    implementation 'org.mariadb.jdbc:mariadb-java-client'
    implementation 'org.springframework.security.oauth:spring-security-oauth2:2.3.5.RELEASE'
    implementation 'org.springframework.security:spring-security-jwt:1.0.10.RELEASE'
    implementation 'io.jsonwebtoken:jjwt:0.9.1'
    implementation 'net.rakugakibox.util:yaml-resource-bundle:1.1'
```



## 테이블 작성 룰

- 1. 테이블명 : 소문자, _ 사용하지 않음
- 2. 테이블 생성시 _seq 를 붙여서 반드시 시퀀스를 생성한다.
- 3. 각각의 테이블에는 id 가 존재하며 해당 sequence를 통하여 테이터를 입력한다.
- 4. 칼럼명
     1) 소문자, _ 사용하지 않음
	 2) 단, Key 라고 생각되는 것은 _ 사용한다.
     3) 또한 시간 같은 경우 _ 사용 

### 네이밍 룰

- 기본 패키지는 com.itmsg이며 프레임워크 단은 episode 패키지, 제품은 제품명으로 패키지를 삼는다.
- Table과 1:1로 매핑되는 Entity의 클래스명은 es prefix를 제거한 테이블 명에 Entity suffix를 붙혀 사용한다.
- Entity와 Dto의 필드명은 underscope를 camelCase로 변경하여 필드명으로 사용한다.
- Mybatis Alias는 Entity를 제거한 클래스명을 camelCase명으로 변환한 것을 사용한다.
```

- api 는 Restful API 규칙을 최대한 따른다.
  - Get : 조회
  - Post : 등록
  - Put : 수정
  - Delete : 삭제
  - url에는 명사복수형 사용(단 모듈로 구분할 경우는 단수 사용)

## 서버단 코딩 규약

- 클래스는 기능별 패키지 안에 Controller, Service, Mapper, Entity, Repository를 한 곳에 모아둔다.
- 패스워드 암호는 BCrypt 암호화를 사용함
- CRUD는 JPA를 사용 뽑아내기 힘든 데이터는 Mybatis를 사용한다.


