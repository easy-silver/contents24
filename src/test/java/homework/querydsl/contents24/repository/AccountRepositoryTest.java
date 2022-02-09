package homework.querydsl.contents24.repository;

import homework.querydsl.contents24.domain.account.Account;
import homework.querydsl.contents24.domain.content.Content;
import homework.querydsl.contents24.domain.employee.Employee;
import homework.querydsl.contents24.domain.platform.Platform;
import homework.querydsl.contents24.domain.account.AccountRepository;
import homework.querydsl.contents24.domain.possession.Possession;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("플랫폼으로 전체 계정 조회")
    void findByPlatform() {
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

        // 인프런 조회
        Platform findPlatform = em.find(Platform.class, 1L);
        List<Long> result = repository.findByPlatform(findPlatform.getId());

        for (Long id : result) {
            System.out.println("id = " + id);
        }

        assertThat(result.size()).isEqualTo(1);
    }

}