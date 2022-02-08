package homework.querydsl.contents24.repository;

import homework.querydsl.contents24.dto.ContentResponseDto;
import homework.querydsl.contents24.dto.ContentSearchCondition;
import homework.querydsl.contents24.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class ContentRepositoryTest {

    @Autowired ContentRepository repository;
    @Autowired PlatformRepository platformRepository;
    @Autowired EntityManager em;

    @Test
    void 컨텐츠_등록() {
        //given
        Platform platform = Platform.builder()
                .name("인프런")
                .link("Inflearn.com")
                .build();
        platformRepository.save(platform);

        Content content = repository.save(Content.builder()
                .name("실전 Querydsl")
                .platform(platform)
                .build());

        //when
        Content findContent = repository.findById(content.getId()).get();

        //then
        assertThat(findContent.getName()).isEqualTo(content.getName());
        assertThat(findContent.getPlatform().getName()).isEqualTo(platform.getName());
        assertThat(findContent.getPlatform().getLink()).isEqualTo(platform.getLink());
    }

    @Test
    void 컨텐츠_전체조회() {
        //given
        Platform platform = Platform.builder()
                .name("인프런")
                .link("Inflearn.com")
                .build();

        platformRepository.save(platform);

        Content content1 = Content.builder()
                .platform(platform)
                .name("실전! Querydsl")
                .build();

        Content content2 = Content.builder()
                .platform(platform)
                .name("실전! Spring Data JPA")
                .build();

        repository.save(content1);
        repository.save(content2);

        //when
        List<Content> contents = repository.findAll();

        //then
        assertThat(contents.get(0).getName()).isEqualTo("실전! Spring Data JPA");
        assertThat(contents.get(0).getPlatform().getName()).isEqualTo("인프런");
    }

    @Test
    void 컨텐츠_단건조회() {
        //given
        Platform platform = Platform.builder()
                .name("인프런")
                .link("Inflearn.com")
                .build();
        platformRepository.save(platform);

        Content content = Content.builder()
                .name("실전! Querydsl")
                .platform(platform)
                .build();

        Long savedId = repository.save(content).getId();

        //when
        Content findContent = repository.findById(savedId).get();

        //then
        assertThat(findContent.getName()).isEqualTo(content.getName());
        assertThat(findContent.getPlatform().getName()).isEqualTo(platform.getName());
    }

    @Test
    void 컨텐츠_수정() {
        //given
        Platform platform = Platform.builder()
                .name("인프런")
                .link("Inflearn.com")
                .build();
        platformRepository.save(platform);

        Content content = repository.save(Content.builder()
                .name("실전 Querydsl")
                .platform(platform)
                .build());
        Long savedId = repository.save(content).getId();

        //when
        content.setName("인프런222");

        //then
        Content findContent = repository.findById(savedId).get();
        assertThat(findContent.getName()).isEqualTo("인프런222");
    }

    @Test
    void 컨텐츠_삭제() {
        //given
        Platform platform = Platform.builder()
                .name("인프런")
                .link("Inflearn.com")
                .build();
        platformRepository.save(platform);

        Content content = repository.save(Content.builder()
                .name("실전 Querydsl")
                .platform(platform)
                .build());
        repository.save(content);

        //when
        repository.delete(content);

        //then
        assertThat(repository.findById(content.getId()).isEmpty()).isTrue();
    }

    @Test
    void 플랫폼으로_조회() {
        //given
        Platform platform = Platform.builder()
                .name("인프런")
                .link("Inflearn.com")
                .build();
        platformRepository.save(platform);

        repository.save(Content.builder()
                .name("실전! Querydsl")
                .platform(platform)
                .build());

        repository.save(Content.builder()
                .name("실전! Spring Data JPA")
                .platform(platform)
                .build());

        //when
        List<Content> contents = repository.findByPlatform(platform);
        for (Content ct : contents) {
            System.out.println("\t\t>>>>> name = " + ct.getName());
        }

        //then
        assertThat(contents.size()).isEqualTo(2);
        assertThat(contents.get(0).getName()).isEqualTo("실전! Querydsl");
    }

    @Test
    void 다중_조건_검색() {
        //given
        String platformName = "테스트 플랫폼";
        Platform platform = Platform.builder()
                .name(platformName)
                .link("test.com")
                .build();
        platformRepository.save(platform);

        String contentName = "테스트 컨텐츠";
        repository.save(Content.builder()
                .name(contentName)
                .platform(platform)
                .build());

        ContentSearchCondition condition = new ContentSearchCondition();
        condition.setContentName(contentName);
        condition.setPlatformName(platformName);

        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<ContentResponseDto> result = repository.search(condition, pageable);

        //then
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo(contentName);
    }

    @Test
    void 계정별_컨텐츠_조회() {
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

        repository.listByAccount(1L);
    }
}