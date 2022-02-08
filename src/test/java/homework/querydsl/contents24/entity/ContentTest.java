package homework.querydsl.contents24.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@SpringBootTest
public class ContentTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void test() {
        Platform platform1 = Platform.builder()
                .name("Inflearn")
                .link("Inflearn.com")
                .build();

        em.persist(platform1);

        Content content1 = Content.builder()
                .platform(platform1)
                .name("Start Querydsl")
                .build();

        em.persist(content1);

        // 초기화
        em.flush();
        em.clear();

        List<Content> contents = em.createQuery("SELECT c FROM Content c", Content.class).getResultList();

        // LAZY 로딩 확인
        for (Content content : contents) {
            System.out.println("contents.platform = " + content.getPlatform());
            System.out.println("contents.name = " + content.getName());
        }
    }
}
