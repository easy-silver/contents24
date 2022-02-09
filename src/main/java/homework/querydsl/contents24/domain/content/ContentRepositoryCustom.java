package homework.querydsl.contents24.domain.content;

import homework.querydsl.contents24.web.dto.response.ContentResponse;
import homework.querydsl.contents24.web.dto.request.ContentSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 컨텐츠 Querydsl 적용 인터페이스
 */
public interface ContentRepositoryCustom {

    /* 다중 조건 검색(페이징) */
    Page<ContentResponse> search(ContentSearchCondition condition, Pageable pageable);

    /* 계정별 보유 컨텐츠 목록 조회 */
    List<ContentResponse> listByAccount(Long accountId);

    /* 해당 컨텐츠의 보유 계정 목록 조회 */
    List<String> accountListByContent(Long contentNo);
}
