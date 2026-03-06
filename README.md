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

## Checked Exception / Unchecked Exception

![alt text](image.png)

### Checked Exception

```java
// Exception 을 상속함으로 CheckedException 처리
public class MustFixException extends Exception{
    public MustFixException(String message) {
        super(message);
    }
}

@RestController
public class MainController {
    @GetMapping
    public ResponseEntity<String> makeCheckedException() throws MustFixException{
        // 에러 생성
        throw new MustFixException("어떻게든 처리를 해줘야 함 try/catch || throw Exception");
    }
}
```

- 컴파일 시 예외에 대한 처리를 강제하여, 처리 하도록 함
  - `throw Exception`
  - `try - catch`
- **사용처** : 실제로 해당 예외에 대한 처리가 가능할 경우 사용한다.
- `Checked Exception`은 기본적으로 \*_트랜잭션이 롤백되지 않음_ (정상적인 흐름의 일부인 '비즈니스적인 예외'로 간주하기 때문입니다.)

### Unchecked Exception

```java
// RuntimeException 을 상속함으로 UncheckedException 처리
public class NotFoundItemException extends RuntimeException{
    public NotFoundItemException(String message) {
        super(message);
    }
}

@RestController
public class MainController {
    @GetMapping("/{itemId}")
    public ResponseEntity<String> makeNotFoundItemError(@PathVariable String itemId){
        // 에러 생성
        throw new NotFoundItemException(itemId);
    }
}

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * UncheckedException 처리
     *
     * @param ex the exception
     * @return the response Entity
     */
    @ExceptionHandler(NotFoundItemException.class)
    public ResponseEntity<String> handleNotFoundItemException(NotFoundItemException ex){
        log.warn("{}를 찾지 못했습니다.", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("아이템을 찾지 못했습니다.");
    }
}
```

- 예외에 대한 처리를 **강제 하지 않음**
- **사용처** : 로직 오류이거나, 호출하는 쪽에서 복구할 방법이 없는 경우에 예외 처리 시 사용
  - 예시: **사용자가 잘못된 파라미터**를 보냈을 때, 조회하려는 데이터가 DB에 없을 경우 활용 (예시 코드 참고)
    - 최근 개발 트렌드: 최근에는 커스텀 예외를 만들 때 대부분 `Unchecked Exception(RuntimeException)을` 상속받아 구현
    - `Checked Exception`은 중간 계층의 코드들이 본인과 상관없는 예외까지 전부 throws로 던져야 해서 **코드가 지저분**해지는 단점이 있음(의존성 전파)
    - 예외가 발생했을 때 로직 내에서 복구하기보다는 빠르게 사용자에게 에러 응답(4xx, 5xx)을 내려주는 것이 더 적합
- `Unchecked Exception`는 기본적으로 **트랜잭션이 롤백** 가능 (시스템적인 '오류'로 간주하기 때문입니다.)
