# Memory Module

## 개요
이 모듈은 Java 애플리케이션의 메모리 관리와 GC(Garbage Collection) 동작을 테스트하고 분석하기 위한 도구입니다.
현재는 OOM(Out Of Memory) 테스트 기능을 제공합니다.

## 기능

### 🧪 OOM 테스트 API

#### 1. 메모리 누수 테스트 (Memory Leak)
- **Endpoint**: `GET /memory/leak`
- **설명**: 500만개의 String 객체를 생성하여 메모리 사용량을 증가시킵니다
- **특징**: 생성된 객체들이 인스턴스 변수에 저장되어 GC되지 않습니다

#### 2. 메모리 누수 테스트 (절반 크기)
- **Endpoint**: `GET /memory/leak/half`
- **설명**: 500만개의 String 객체를 로컬 변수로 생성합니다
- **특징**: 메소드 종료 후 GC 대상이 됩니다

#### 3. 메모리 정리
- **Endpoint**: `GET /memory/clear`
- **설명**: 저장된 메모리 객체들을 정리합니다
- **동작**: `memory.clear()`를 호출하여 참조를 해제합니다

#### 4. 헬스 체크
- **Endpoint**: `GET /memory/health-check`
- **설명**: 애플리케이션 상태를 확인합니다

## 🚀 실행 방법

### 필수 VMOption 설정

OOM 테스트를 위해서는 **반드시** 다음 VMOption을 설정해야 합니다:

```bash
-Xlog:gc*:file=gc.log:time,uptime,level,tags -Xmn100m -Xmx500m
```

#### VMOption 설명
- `-Xlog:gc*:file=gc.log:time,uptime,level,tags`: GC 로그를 gc.log 파일에 상세히 기록
- `-Xmn100m`: Young Generation 크기를 100MB로 설정
- `-Xmx500m`: 최대 힙 메모리를 500MB로 제한

### IDE에서 실행
1. **IntelliJ IDEA**:
   - Run Configuration → VM options에 위 옵션 추가
   - 또는 `MemoryApplication` 클래스 상단 주석 참조

2. **Eclipse**:
   - Run Configurations → Arguments → VM arguments에 추가

### 명령줄에서 실행
```bash
java -Xlog:gc*:file=gc.log:time,uptime,level,tags -Xmn100m -Xmx500m -jar memory-module.jar
```

## 📊 테스트 시나리오

### 1. 기본 메모리 누수 테스트
```bash
# 1. 메모리 누수 발생
curl http://localhost:8080/memory/leak

# 2. GC 로그 확인
tail -f gc.log

# 3. 메모리 정리
curl http://localhost:8080/memory/clear
```

### 2. GC 동작 분석
```bash
# 절반 크기 테스트 (로컬 변수)
curl http://localhost:8080/memory/leak/half

# GC 로그에서 Young GC 발생 확인
grep "Pause Young" gc.log
```

## 📈 GC 로그 분석

생성되는 `gc.log` 파일에서 다음 정보를 확인할 수 있습니다:

- **Young GC**: `Pause Young (Normal)`
- **Mixed GC**: `Pause Young (Mixed)`
- **Full GC**: `Pause Full`
- **Concurrent Mark Cycle**: 백그라운드 마킹 작업
- **메모리 사용량**: `Before->After(Total)`

### 주요 GC 패턴
```
[시간][업타임][레벨][태그] GC(번호) 타입 메모리변화 소요시간
```

예시:
```
[2025-08-11T16:18:27.168+0900][221.620s][info][gc] GC(55) Pause Remark 471M->65M(224M) 7.500ms
```

## 📊 실시간 GC 모니터링 (VisualVM)

### VisualVM 사용 권장

**VisualVM**은 Java 애플리케이션의 메모리 사용량과 GC 동작을 실시간으로 시각적으로 모니터링할 수 있는 강력한 도구입니다.



## 🔧 개발 환경

- **Java**: 17
- **Spring Boot**: 3.x
- **GC**: G1GC (기본값)
- **아키텍처**: Spring WebMVC

## ⚠️ 주의사항

1. **VMOption 필수**: 위에 명시된 VMOption 없이는 정확한 테스트가 불가능합니다
2. **메모리 제한**: `-Xmx500m` 설정으로 인해 OOM이 발생할 수 있습니다
3. **로그 파일**: `gc.log` 파일이 프로젝트 루트에 생성됩니다
4. **운영 환경 주의**: 이 모듈은 테스트 목적으로만 사용하세요

## 📝 로그 예시

### Young GC 발생
```
[2025-08-11T16:18:27.116+0900][221.569s][info][gc] GC(54) Pause Young (Concurrent Start) (G1 Preventive Collection) 484M->470M(504M) 5.220ms
```

### Concurrent Mark Cycle
```
[2025-08-11T16:18:27.168+0900][221.620s][info][gc] GC(55) Pause Remark 471M->65M(224M) 7.500ms
```

### Full GC 발생
```
[2025-08-11T16:25:36.156+0900][650.619s][info][gc] GC(76) Pause Full (G1 Compaction Pause) 502M->173M(504M) 45.775ms
```
