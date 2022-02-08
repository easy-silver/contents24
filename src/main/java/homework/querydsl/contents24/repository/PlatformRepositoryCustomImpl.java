package homework.querydsl.contents24.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import homework.querydsl.contents24.dto.PlatformResponseDto;
import homework.querydsl.contents24.dto.PlatformSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static homework.querydsl.contents24.entity.QPlatform.platform;

/**
 * 플랫폼 Querydsl 구현 클래스
 */
public class PlatformRepositoryCustomImpl implements PlatformRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PlatformRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 플랫폼 검색 조회
     * 검색 조건: 이름, 링크 (없을 경우 전체 조회)
     * 이름 오름차순으로 정렬
     * @param condition
     * @param pageable
     * @return
     */
    @Override
    public Page<PlatformResponseDto> search(PlatformSearchCondition condition, Pageable pageable) {
        QueryResults<PlatformResponseDto> results = queryFactory
                .select(Projections.bean(
                    PlatformResponseDto.class,
                    platform.id,
                    platform.name,
                    platform.link))
                .from(platform)
                //동적 조건 처리(이름, 링크)
                .where(nameEq(condition.getPlatformName()),
                        linkLike(condition.getPlatformLink()))
                //정렬
                .orderBy(platform.name.asc())
                //페이징 처리
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PlatformResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    /* 플랫폼명 Equal 조건 */
    private BooleanExpression nameEq(String platformName) {
        return platformName != null ? platform.name.eq(platformName) : null;
    }

    /* 링크 Like 조건 */
    private Predicate linkLike(String platformLink) {
        return platformLink != null ? platform.link.contains(platformLink) : null;
    }
}
