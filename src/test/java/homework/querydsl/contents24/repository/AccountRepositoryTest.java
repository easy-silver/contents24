package homework.querydsl.contents24.repository;

import homework.querydsl.contents24.entity.Platform;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class AccountRepositoryTest {

    @Autowired AccountRepository repository;
    @Autowired EntityManager em;

    @Test
    void findByPlatform() {
        // 인프런 조회
        Platform platform = em.find(Platform.class, 1L);
        List<Long> result = repository.findByPlatform(platform.getId());

        for (Long id : result) {
            System.out.println("id = " + id);
        }

        assertThat(result.size()).isEqualTo(1);
    }

}