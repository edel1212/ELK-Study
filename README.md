# ELK-Study

## Logback 
- **정의**: 현재 가장 널리 쓰이는 자바 기반 로깅 프레임워크이다.
- `spring-boot-starter`에 **기본 포함**되어 있으며, 별도 설정 없이도 바로 사용 가능
- **주요 특징**: `Log4j` 대비 월등한 속도와 메모리 사용량이 적다
  - 설정 파일 변경 시 서버 재시작 없이 자동 리로딩 지원. 
- **사용법**: `@Slf4j` 어노테이션 선언을 통해 **객체 생성 없이 즉시 사용 가능**하다.
  - `PSA(Portable Service Abstraction)`에 따라, 개발자가 구현체에 종속되지 않고 추상화된 인터페이스를 사용하기 때문임
    - 같은 사용 예시 - `@Transactional`

### Slf4j (Simple Logging Facade for Java)
![img.png](img.png)
- 로깅 프레임워크들을 추상화한 **인터페이스(Facade)** 이다.
  - `Logback, Log4j, Log4j2` 등등 ...
- **장점**: `@Slf4j`를 사용하면 코드 변경 없이 설정(의존성)만으로 `Logback -> Log4j2` 등으로 **손쉽게 전환** 할 수 있다.
