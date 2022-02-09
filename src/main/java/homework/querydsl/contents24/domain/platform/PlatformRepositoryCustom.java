package homework.querydsl.contents24.domain.platform;

import homework.querydsl.contents24.web.dto.response.PlatformResponse;
import homework.querydsl.contents24.web.dto.request.PlatformSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 플랫폼 Querydsl 인터페이스
 */
public interface PlatformRepositoryCustom {

    /* 검색 조회 */
    Page<PlatformResponse> search(PlatformSearchCondition condition, Pageable pageable);

}
