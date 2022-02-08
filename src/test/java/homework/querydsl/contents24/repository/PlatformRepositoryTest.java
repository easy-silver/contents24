package homework.querydsl.contents24.repository;

import homework.querydsl.contents24.dto.PlatformResponseDto;
import homework.querydsl.contents24.dto.PlatformSearchCondition;
import homework.querydsl.contents24.entity.Platform;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Commit
@Transactional
@SpringBootTest
class PlatformRepositoryTest {

    @Autowired PlatformRepository repository;
    @Autowired AccountRepository accountRepository;
    @Autowired PossessionRepository possessionRepository;
    @Autowired ContentRepository contentRepository;

    @PersistenceContext EntityManager em;

    @Test
    void 플랫폼_등록() {
        //given
        Platform platform = Platform.builder()
                .name("인프런")
                .link("inflearn.com")
                .build();

        //when
        repository.save(platform);

        //then
        Platform findPlatform = repository.findById(platform.getId()).get();
        assertThat(platform).isEqualTo(findPlatform);
        assertThat(platform.getName()).isEqualTo(findPlatform.getName());
        assertThat(platform.getLink()).isEqualTo(findPlatform.getLink());
    }

    @Test
    void 플랫폼_전체조회() {
        //given
        Platform platform1 = Platform.builder()
                .name("인프런")
                .link("inflearn.com")
                .build();
        repository.save(platform1);

        Platform platform2 = Platform.builder()
                .name("프로그래머스")
                .link("programmers.com")
                .build();
        repository.save(platform2);

        //when
        List<Platform> platforms = repository.findAll();

        //then
        assertThat(platforms.get(0).getName()).isEqualTo("프로그래머스");
        assertThat(platforms.get(0).getLink()).isEqualTo("programmers.com");
    }

    @Test
    void 플랫폼_단건조회() {
        //given
        Platform platform1 = Platform.builder()
                .name("인프런")
                .link("inflearn.com")
                .build();
        Long platformOneId = repository.save(platform1).getId();

        Platform platform2 = Platform.builder()
                .name("프로그래머스")
                .link("programmers.com")
                .build();
        Long platformTwoId = repository.save(platform2).getId();

        //when
        Platform findFirst = repository.findById(platformOneId).get();
        Platform findSecond = repository.findById(platformTwoId).get();

        //then
        assertThat(findFirst.getName()).isEqualTo("인프런");
        assertThat(findSecond.getName()).isEqualTo("프로그래머스");
    }

    @Test
    void 플랫폼_수정() {
        //given
        Platform orgPlatform = Platform.builder()
                .name("인프런")
                .link("inflearn.com")
                .build();
        repository.save(orgPlatform);

        //when
        orgPlatform.setName("인프런222");
        orgPlatform.setLink("inflearn222.com");
        Platform newPlatform = repository.findById(orgPlatform.getId()).get();

        //then
        assertThat(newPlatform.getName()).isEqualTo("인프런222");
    }

    @Test
    void 플랫폼_삭제() {
        //given
        Platform platform = Platform.builder()
                .name("인프런")
                .link("inflearn.com")
                .build();
        repository.save(platform);

        //when
        repository.delete(platform);

        //then
        assertThat(repository.findById(platform.getId()).isEmpty()).isTrue();
    }

    @Test
    void 플랫폼_순차_삭제() {

        // 1.플랫폼에 해당하는 계정 번호 확인(리스트)
        Platform platform = repository.findById(1L).get();
        List<Long> ids = accountRepository.findByPlatform(platform.getId());

        // 2.계정 번호 리스트로 보유 데이터 삭제
        possessionRepository.deleteAllByAccount(ids);
        /*em.createQuery("DELETE FROM Possession p WHERE p.account IN :accountNos")
                .setParameter("accountNos", accountList)
                .executeUpdate();*/

        // 3.계정 삭제(플랫폼 번호로 삭제)
        accountRepository.deleteAllByPlatform(platform.getId());
        /*em.createQuery("DELETE FROM Account a WHERE a.platform.id = :platformId")
                .setParameter("platformId", platform.getId())
                .executeUpdate();*/

        // 4.플랫폼 번호로 컨텐츠 삭제
        contentRepository.deleteAllByPlatform(platform.getId());
        /*em.createQuery("DELETE FROM Content c WHERE c.platform.id = : platformNo")
                .setParameter("platformNo", platform.getId())
                .executeUpdate();*/

        // 플랫폼 삭제
        repository.delete(platform);
    }

    @Test
    void 플랫폼_검색() {
        //given
        repository.save(Platform.builder()
                .name("테스트")
                .link("test.com")
                .build());

        repository.save(Platform.builder()
                .name("테스트2")
                .link("test2.com")
                .build());

        PlatformSearchCondition condition = new PlatformSearchCondition();
        //condition.setPlatformName("테스트");
        condition.setPlatformLink("test");
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<PlatformResponseDto> platforms = repository.search(condition, pageable);

        //then
        assertThat(platforms.getTotalElements()).isEqualTo(2);
        assertThat(platforms.getTotalPages()).isEqualTo(1);
        assertThat(platforms.getContent().get(0).getName()).isEqualTo("테스트");
    }
}