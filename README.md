# contents24

## 목표
Spring Data JPA와 Querydsl을 활용하여 간단한 REST API 개발

## 내용
회사에서 보유(구매)한 컨텐츠들을 관리하는 API

## 예시
이지은 사원이 jelee@snack24h.com 계정으로 인프런의 ‘실전 Querydsl’ 강의를 보유하고 있음

## 조건
* 사원 테이블의 PK는 복합키로 지정(부서번호, 사원번호)
* 목록 조회는 페이징 처리를 해야 하며 동적 검색 기능을 구현한다.

## 주요 엔티티
* 플랫폼(Platform)
* 컨텐츠(Content)
* 보유(Possession)
* 계정(Account)
* 사원(Employee)

## 연관관계
- 플랫폼 - 컨텐츠 (1:N)
- 컨텐츠 - 계정 (N:M): 다대다 관계이므로 중간에 '보유' 테이블을 둬 1:N, N:1 관계로 풀어낸다.
  - 컨텐츠 - 보유 (1:N)
  - 보유 - 계정 (N:1)
- 사원 - 계정 (1:N)

## API 명세(Swagger UI)
http://localhost:8080/swagger-ui.html