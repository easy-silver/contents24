package homework.querydsl.contents24.repository;

import homework.querydsl.contents24.domain.account.AccountRepository;
import homework.querydsl.contents24.domain.content.ContentRepository;
import homework.querydsl.contents24.domain.platform.Platform;
import homework.querydsl.contents24.domain.platform.PlatformRepository;
import homework.querydsl.contents24.domain.possession.PossessionRepository;
import homework.querydsl.contents24.web.dto.request.PlatformSearchCondition;
import homework.querydsl.contents24.web.dto.response.PlatformResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class PlatformRepositoryTest {

    @Autowired PlatformRepository repository;
    @Autowired AccountRepository accountRepository;
    @Autowired PossessionRepository possessionRepository;
    @Autowired ContentRepository contentRepository;

    @Test
    @DisplayName("플랫폼 등록")
    void create() {
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
    @DisplayName("플랫폼 전체 조회")
    void findAll() {
        //given
        Platform platform1 = Platform.builder()
                .name("인프런")
                .link("inflearn.com")
                .build();

        Platform platform2 = Platform.builder()
                .name("프로그래머스")
                .link("programmers.com")
                .build();

        repository.save(platform2);
        repository.save(platform1);

        //when
        List<Platform> platforms = repository.findAllPlatforms();

        //then
        assertThat(platforms.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("플랫폼 단건 조회")
    void findById() {
        //given
        Platform platform1 = Platform.builder()
                .name("인프런")
                .link("inflearn.com")
                .build();

        Platform platform2 = Platform.builder()
                .name("프로그래머스")
                .link("programmers.com")
                .build();

        Long platform1Id = repository.save(platform1).getId();
        Long platform2Id = repository.save(platform2).getId();

        //when
        Platform findFirst = repository.findById(platform1Id).get();
        Platform findSecond = repository.findById(platform2Id).get();

        //then
        assertThat(findFirst.getName()).isEqualTo("인프런");
        assertThat(findSecond.getName()).isEqualTo("프로그래머스");
    }

    @Test
    @DisplayName("플랫폼 수정")
    void update() {
        //given
        Platform orgPlatform = Platform.builder()
                .name("인프런")
                .link("inflearn.com")
                .build();
        repository.save(orgPlatform);

        //when
        orgPlatform.update("인프런222", "inflearn222.com");
        Platform newPlatform = repository.findById(orgPlatform.getId()).get();

        //then
        assertThat(newPlatform.getName()).isEqualTo("인프런222");
    }

    @Test
    @DisplayName("플랫폼 삭제")
    void delete() {
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
    @DisplayName("플랫폼 검색")
    void search() {
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
        Page<PlatformResponse> platforms = repository.search(condition, pageable);

        //then
        assertThat(platforms.getTotalElements()).isEqualTo(2);
        assertThat(platforms.getTotalPages()).isEqualTo(1);
        assertThat(platforms.getContent().get(0).getName()).isEqualTo("테스트");
    }
}