package homework.querydsl.contents24.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import homework.querydsl.contents24.dto.ContentResponseDto;
import homework.querydsl.contents24.dto.ContentSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static homework.querydsl.contents24.entity.QAccount.account;
import static homework.querydsl.contents24.entity.QContent.content;
import static homework.querydsl.contents24.entity.QPlatform.platform;
import static homework.querydsl.contents24.entity.QPossession.possession;

/**
 * 컨텐츠 Querydsl 구현 클래스
 */
public class ContentRepositoryCustomImpl implements ContentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ContentRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 컨텐츠 검색 조회
     * 컨텐츠 이름 오름차순으로 정렬
     * 검색 조건: 플랫폼명, 컨텐츠명
     * @param condition
     * @param pageable
     * @return
     */
    @Override
    public Page<ContentResponseDto> search(ContentSearchCondition condition, Pageable pageable) {
        QueryResults<ContentResponseDto> results = queryFactory
                .select(Projections.fields(ContentResponseDto.class,
                        platform.name.as("platformName"),
                        content.id,
                        content.name))
                .from(content)
                //플랫폼 엔티티와 조인
                .leftJoin(content.platform, platform)
                .where(platformNameEq(condition.getPlatformName()),
                        contentNameLike(condition.getContentName()))
                //정렬
                .orderBy(content.name.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ContentResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    /**
     * 계정별 목록 조회
     * @param accountNo
     * @return
     */
    @Override
    public List<ContentResponseDto> listByAccount(Long accountNo) {
        return queryFactory
                .select(Projections.fields(ContentResponseDto.class,
                        platform.name.as("platformName"),
                        content.id,
                        content.name))
                .from(possession)
                .innerJoin(possession.content.platform, platform)
                .innerJoin(possession.content, content)
                .innerJoin(possession.account, account)
                .where(possession.account.id.eq(accountNo))
                .fetch();
    }

    /**
     * 단일 컨텐츠의 보유 계정 목록
     * @param contentNo
     * @return
     */
    @Override
    public List<String> accountListByContent(Long contentNo) {
        return queryFactory
                .select(account.accountId)
                .from(possession)
                .innerJoin(possession.content, content)
                .innerJoin(possession.account, account)
                .where(possession.content.id.eq(contentNo))
                .fetch();
    }

    /* 플랫폼명 검색 조건 추가 */
    private BooleanExpression platformNameEq(String platformName) {
        return platformName != null ? content.platform.name.eq(platformName) : null;
    }

    /* 컨텐츠명 검색 조건 추가 */
    private BooleanExpression contentNameLike(String contentName) {
        return contentName != null ? content.name.contains(contentName) : null;
    }
}
