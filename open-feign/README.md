# Open Feign 모듈

## 개요
이 모듈은 Spring Cloud OpenFeign을 사용하여 외부 API와의 연동을 구현하는 예제 프로젝트입니다.
Feign은 선언적 웹 서비스 클라이언트로, 인터페이스만 정의하면 자동으로 HTTP 클라이언트를 생성해주는 라이브러리입니다.

## 주요 기능
- **선언적 HTTP 클라이언트**: `@FeignClient` 어노테이션을 통한 간편한 API 클라이언트 정의
- **다양한 HTTP 메소드 지원**: GET, POST 요청 처리
- **파라미터 처리**: Path Variable, Query Parameter, Request Body 지원
- **재시도 및 타임아웃**: 네트워크 장애 시 자동 재시도 기능
- **로깅**: 상세한 HTTP 요청/응답 로깅
- **DateTime 포맷팅**: ISO 표준 날짜/시간 포맷 지원

## 프로젝트 구조
```
open-feign/
├── src/main/java/com/ch/openfeign/
│   ├── OpenFeignApplication.java          # 메인 애플리케이션 클래스
│   ├── client/
│   │   └── OpenFeignClient.java           # Feign 클라이언트 인터페이스
│   ├── config/
│   │   └── OpenFeignConfig.java           # Feign 설정 클래스
│   └── controller/
│       └── OpenFeignController.java       # REST 컨트롤러
├── src/test/java/
│   └── com/ch/openfeign/client/
│       └── OpenFeignClientTest.java       # 클라이언트 테스트
└── open-api-server/                       # 테스트용 API 서버
    └── src/main/java/com/ch/openfeign/openapi/
        └── controller/
            └── FeignOpenApiController.java
```

## 의존성
```gradle
ext {
    set('springCloudVersion', "2024.0.2")
}

dependencyManagement {
    imports {
        mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
    }
}

dependencies {
    implementation('org.springframework.boot:spring-boot-starter-web')
    implementation('org.springframework.cloud:spring-cloud-starter-openfeign')
}
```

## 핵심 클래스 설명

### 1. OpenFeignClient
```java
@FeignClient(name = "open-api-server", url = "http://localhost:8081/ch/openfeign/open-api")
public interface OpenFeignClient {
    @GetMapping("/hello-world")
    String helloWorld();
    
    @GetMapping("/time-out")
    String timeOut() throws RetryableException;
    
    @GetMapping("/path-variable/{id}")
    String pathVariable(@PathVariable String id);
    
    @GetMapping("/param-variable")
    String paramVariable(@RequestParam String id);
    
    @PostMapping("/create/without-body")
    String createWithoutBody();
    
    @PostMapping("/create/with-body")
    String createOfWithBody(@RequestBody String body);
}
```

### 2. OpenFeignConfig
Feign 클라이언트의 동작을 설정하는 구성 클래스입니다.

**주요 설정:**
- **재시도 정책**: 100ms 간격으로 최대 3초까지, 총 5회 재시도
- **로그 레벨**: FULL (요청/응답 헤더, 바디 모두 로깅)
- **DateTime 포맷터**: ISO 표준 포맷 사용

```java
@Configuration
@EnableFeignClients(basePackages = "com.ch.openfeign")
public class OpenFeignConfig {
    @Bean
    Retryer.Default retryer(){
        return new Retryer.Default(100L, TimeUnit.SECONDS.toMillis(3L), 5);
    }
    
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
```


## 테스트


### 주요 테스트 케이스
- **기본 API 호출**: Hello World 응답 검증
- **타임아웃 처리**: RetryableException 발생 확인
- **Path Variable**: 경로 변수 전달 및 응답 검증
- **Query Parameter**: 쿼리 파라미터 전달 및 응답 검증
- **POST 요청**: Body 포함/미포함 POST 요청 테스트

```java
@FeignTest
@DisplayName("OpenFeignClientTest")
class OpenFeignClientTest {
    @Autowired
    private OpenFeignClient openFeignClient;
    
    @Test
    @DisplayName("기본 Hello World 응답을 정상적으로 반환하는지 테스트")
    void helloWorld() {
        String response = openFeignClient.helloWorld();
        assertThat(response).isEqualTo("Hello World");
    }
    
    @Test
    @DisplayName("타임아웃 발생 시 RetryableException이 발생하는지 테스트")
    void timeOut(){
        assertThatThrownBy(() -> openFeignClient.timeOut())
            .isInstanceOf(RetryableException.class);
    }
}
```

## 설정 옵션

### application.yml 설정 예제
```yaml
spring:
  cloud:
    openfeign:
      client:
        config:
          default:
            connectTimeout: 5000
            readTimeout: 5000

logging:
  level:
    com.ch.openfeign.client: DEBUG
```
