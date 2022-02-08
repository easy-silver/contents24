package homework.querydsl.contents24.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class PossessionTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void test() {
        // 플랫폼 생성
        Platform platform = Platform.builder()
                .name("Inflearn")
                .link("Inflearn.com")
                .build();
        em.persist(platform);

        // 컨텐트 생성
        Content content = Content.builder()
                .platform(platform)
                .name("Start Querydsl")
                .build();
        em.persist(content);

        // 사원 생성
        Employee employee = Employee.builder()
                .employeeNo(111L)
                .deptNo(999L)
                .name("leejieun")
                .build();
        em.persist(employee);

        // 계정 생성
        Account account = Account.builder()
                .accountId("jelee@snack24h.com")
                .employee(employee)
                .platform(platform)
                .build();
        em.persist(account);

        // 보유 데이터 생성
        Possession possession = Possession.builder()
                .content(content)
                .account(account)
                .build();
        em.persist(possession);

        em.flush();
        em.clear();

        List<Possession> resultList = em.createQuery("SELECT p FROM Possession p", Possession.class)
                .getResultList();

        for (Possession ps : resultList) {
            System.out.println(">>>>>\t\tps.account = " + ps.getAccount());
            System.out.println(">>>>>\t\tps.content = " + ps.getContent());
        }

        assertThat(resultList).isNotNull();
        assertThat(resultList.size()).isGreaterThan(0);
        assertThat(resultList.get(0).getAccount().getAccountId()).isEqualTo("jelee@snack24h.com");
    }
}