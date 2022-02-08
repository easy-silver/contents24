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
class AccountTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void 계정_등록() {
        //given
        Employee employee = Employee.builder()
                .employeeNo(111L)
                .deptNo(999L)
                .name("leejieun")
                .build();

        em.persist(employee);

        Platform platform = Platform.builder()
                .name("Inflearn")
                .link("Inflearn.com")
                .build();

        em.persist(platform);

        String accountId = "jelee@snack24h.com";
        Account account = Account.builder()
                .accountId(accountId)
                .employee(employee)
                .platform(platform)
                .build();

        em.persist(account);

        em.flush();
        em.clear();

        //when
        List<Account> accounts = em.createQuery("SELECT a FROM Account a", Account.class)
                .getResultList();

        //then
        assertThat(accounts.get(0).getAccountId()).isEqualTo(accountId);
    }
}