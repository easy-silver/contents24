package homework.querydsl.contents24.repository;

import homework.querydsl.contents24.domain.account.Account;
import homework.querydsl.contents24.domain.content.Content;
import homework.querydsl.contents24.domain.content.ContentRepository;
import homework.querydsl.contents24.domain.employee.Employee;
import homework.querydsl.contents24.domain.platform.Platform;
import homework.querydsl.contents24.domain.platform.PlatformRepository;
import homework.querydsl.contents24.domain.possession.Possession;
import homework.querydsl.contents24.web.dto.request.ContentSearchCondition;
import homework.querydsl.contents24.web.dto.response.ContentResponse;
import org.junit.jupiter.api.DisplayName;
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

    @Autowired ContentRepository contentRepository;
    @Autowired PlatformRepository platformRepository;
    @Autowired EntityManager em;

    @Test
    @DisplayName("컨텐츠 등록")
    void create() {
        //given
        Platform platform = Platform.builder()
                .name("인프런")
                .link("Inflearn.com")
                .build();

        Content content = Content.builder()
                .name("실전 Querydsl")
                .platform(platform)
                .build();

        //when
        platformRepository.save(platform);
        contentRepository.save(content);

        //then
        Content findContent = contentRepository.findById(content.getId()).get();

        assertThat(findContent.getName()).isEqualTo(content.getName());
        assertThat(findContent.getPlatform().getName()).isEqualTo(platform.getName());
        assertThat(findContent.getPlatform().getLink()).isEqualTo(platform.getLink());
    }

    @Test
    @DisplayName("컨텐츠 전체 조회")
    void findAll() {
        //given
        Platform platform = Platform.builder()
                .name("인프런")
                .link("Inflearn.com")
                .build();

        Content content1 = Content.builder()
                .platform(platform)
                .name("실전! Querydsl")
                .build();

        Content content2 = Content.builder()
                .platform(platform)
                .name("실전! Spring Data JPA")
                .build();

        platformRepository.save(platform);
        contentRepository.save(content1);
        contentRepository.save(content2);

        //when
        List<Content> contents = contentRepository.findAll();

        //then
        assertThat(contents.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("컨텐츠 단건 조회")
    void findById() {
        //given
        Platform platform = Platform.builder()
                .name("인프런")
                .link("Inflearn.com")
                .build();

        Content content = Content.builder()
                .name("실전! Querydsl")
                .platform(platform)
                .build();

        platformRepository.save(platform);
        Long savedId = contentRepository.save(content).getId();

        //when
        Content findContent = contentRepository.findById(savedId).get();

        //then
        assertThat(findContent.getName()).isEqualTo(content.getName());
        assertThat(findContent.getPlatform().getName()).isEqualTo(platform.getName());
    }

    @Test
    @DisplayName("컨텐츠 수정")
    void update() {
        //given
        Platform platform = Platform.builder()
                .name("인프런")
                .link("Inflearn.com")
                .build();
        platformRepository.save(platform);

        Content content = contentRepository.save(Content.builder()
                .name("실전 Querydsl")
                .platform(platform)
                .build());
        Long savedId = contentRepository.save(content).getId();

        //when
        content.setName("인프런222");

        //then
        Content findContent = contentRepository.findById(savedId).get();
        assertThat(findContent.getName()).isEqualTo("인프런222");
    }

    @Test
    @DisplayName("컨텐츠 삭제")
    void delete() {
        //given
        Platform platform = Platform.builder()
                .name("인프런")
                .link("Inflearn.com")
                .build();
        platformRepository.save(platform);

        Content content = contentRepository.save(Content.builder()
                .name("실전 Querydsl")
                .platform(platform)
                .build());
        contentRepository.save(content);

        //when
        contentRepository.delete(content);

        //then
        assertThat(contentRepository.findById(content.getId()).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("플랫폼으로 조회")
    void findByPlatform() {
        //given
        Platform platform = Platform.builder()
                .name("인프런")
                .link("Inflearn.com")
                .build();
        platformRepository.save(platform);

        contentRepository.save(Content.builder()
                .name("실전! Querydsl")
                .platform(platform)
                .build());

        contentRepository.save(Content.builder()
                .name("실전! Spring Data JPA")
                .platform(platform)
                .build());

        //when
        List<Content> contents = contentRepository.findByPlatform(platform);
        for (Content ct : contents) {
            System.out.println("\t\t>>>>> name = " + ct.getName());
        }

        //then
        assertThat(contents.size()).isEqualTo(2);
        assertThat(contents.get(0).getName()).isEqualTo("실전! Querydsl");
    }

    @Test
    @DisplayName("다중 조건 검색")
    void search() {
        //given
        String platformName = "테스트 플랫폼";
        Platform platform = Platform.builder()
                .name(platformName)
                .link("test.com")
                .build();
        platformRepository.save(platform);

        String contentName = "테스트 컨텐츠";
        contentRepository.save(Content.builder()
                .name(contentName)
                .platform(platform)
                .build());

        ContentSearchCondition condition = new ContentSearchCondition();
        condition.setContentName(contentName);
        condition.setPlatformName(platformName);

        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<ContentResponse> result = contentRepository.search(condition, pageable);

        //then
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo(contentName);
    }

    @Test
    @DisplayName("계정별 컨텐츠 조회")
    void findByAccount() {
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

        //ToDo. findByAccount로 변경 필요
        contentRepository.listByAccount(1L);
    }
}