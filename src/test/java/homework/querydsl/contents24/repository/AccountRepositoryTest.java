package homework.querydsl.contents24.repository;

import homework.querydsl.contents24.domain.account.Account;
import homework.querydsl.contents24.domain.account.AccountRepository;
import homework.querydsl.contents24.domain.platform.Platform;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@SpringBootTest
class AccountRepositoryTest {

    @Autowired EntityManager em;
    @Autowired AccountRepository repository;

    @Test
    @DisplayName("플랫폼으로 전체 계정 조회")
    void findByPlatform_new() {
        //given
        Platform platform = Platform.builder()
                .name("Inflearn")
                .link("Inflearn.com")
                .build();

        em.persist(platform);

        //when
        List<Account> accounts = repository.findByPlatform(platform);

        //then
        System.out.println("accounts.size() = " + accounts.size());
    }

}