package homework.querydsl.contents24.domain.content;

import homework.querydsl.contents24.domain.platform.Platform;
import homework.querydsl.contents24.web.dto.request.ContentUpdateRequest;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@ToString(of = {"id", "name"})
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Content {

    // 컨텐츠 번호
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "content_no")
    private Long id;

    // 컨텐츠 이름
    @Column(name = "content_name", nullable = false)
    private String name;

    // 플랫폼 정보
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "platform_no")
    private Platform platform;

    @Builder
    public Content(String name, Platform platform) {
        this.name = name;
        if (platform != null) {
            changePlatform(platform);
        }
    }

    public void update(ContentUpdateRequest requestDto) {
        this.name = requestDto.getName();
    }

    public void changePlatform(Platform platform) {
        this.platform = platform;
    }
}