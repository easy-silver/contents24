package homework.querydsl.contents24.entity;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static homework.querydsl.contents24.entity.QPlatform.platform;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 플랫폼 Querydsl Test
 */
@Transactional
@SpringBootTest
class PlatformTest {

    @PersistenceContext EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
/*
        Platform platform1 = Platform.builder()
                .name("인프런")
                .link("inflearn.com")
                .build();

        Platform platform2 = Platform.builder()
                .name("유튜브")
                .link("youtube.com")
                .build();

        em.persist(platform1);
        em.persist(platform2);*/
    }

    @Test
    public void 플랫폼_검색() {
        Platform findPlatform = queryFactory
                .select(platform)
                .from(platform)
                .where(platform.name.eq("인프런"))
                .fetchOne();

        assertThat(findPlatform.getName()).isEqualTo("인프런");
    }

    @Test
    void 플랫폼_and조건_검색() {
        Platform findPlatform = queryFactory
                .selectFrom(platform)
                .where(platform.name.eq("인프런")
                        .and(platform.link.contains("inflearn")))
                .fetchOne();

        assertThat(findPlatform.getLink()).isEqualTo("inflearn.com");
        assertThat(findPlatform.getName()).isEqualTo("인프런");
    }

    @Test
    void 결과조회() {
        //given
        QueryResults<Platform> results = queryFactory
                .selectFrom(platform)
                .fetchResults();
        //when
        List<Platform> platforms = results.getResults();
        long totalCount = results.getTotal();

        //then
        assertThat(platforms.size()).isEqualTo(totalCount);
    }

    @Test
    void 플랫폼_정렬() {
        //given
        em.persist(Platform.builder()
                .name("프로그래머스")
                .link("programmers.com")
                .build());

        em.persist(Platform.builder()
                .name("노마드코더")
                .link("nomardcoders.com")
                .build());

        //when
        List<Platform> platforms = queryFactory
                .selectFrom(platform)
                // 플랫폼명 오름차순(가나다순)으로 정렬
                .orderBy(platform.name.asc())
                .fetch();

        for (Platform pf : platforms) {
            System.out.println(">>>\t\tpf = " + pf.getName());
        }

        //then
        assertThat(platforms.get(0).getName()).isEqualTo("노마드코더");
    }

    @Test
    void 페이징_조회() {
        QueryResults<Platform> queryResults = queryFactory
                .selectFrom(platform)
                .orderBy(platform.name.asc())
                .offset(0)
                .limit(1)
                .fetchResults();

        assertThat(queryResults.getLimit()).isEqualTo(1);
        assertThat(queryResults.getResults().size()).isEqualTo(1);
        assertThat(queryResults.getOffset()).isEqualTo(0);
    }

    @Test
    void 이름and링크_검색() {
        //given
        String platformNameParam = "인프런";
        String platformLinkParam = "inflearn";

        //when
        List<Platform> result = searchPlatform(platformNameParam, platformLinkParam);

        //then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void 조건없이_검색() {
        //when
        List<Platform> result = searchPlatform(null, null);

        //then
        assertThat(result.size()).isEqualTo(3);
    }

    /* 검색 조건 확인 */
    private List<Platform> searchPlatform(String platformName, String link) {
        return queryFactory
                .selectFrom(platform)
                .where(nameEq(platformName),
                        linkLike(link))
                .fetch();
    }

    /* 플랫폼명 검색 조건 추가 */
    private BooleanExpression nameEq(String platformName) {
        return platformName != null ? platform.name.eq(platformName) : null;
    }

    /* 링크 검색 조건 추가 */
    private BooleanExpression linkLike(String link) {
        return link != null ? platform.link.contains(link) : null;
    }

    @Test
    public void testJPQL() {
        // 영속성 컨텍스트 초기화
        em.flush();
        em.clear();

        List<Platform> platforms = em.createQuery("SELECT p FROM Platform p", Platform.class)
                .getResultList();

        for (Platform pf : platforms) {
            System.out.println("platform.name = " + pf.getName());
            System.out.println("platform.link = " + pf.getLink());
            System.out.println();
        }

        assertThat(platforms.size()).isEqualTo(3);
        assertThat(platforms.get(0).getName()).isEqualTo("인프런");
    }

}