package homework.querydsl.contents24.entity;

import homework.querydsl.contents24.dto.ContentUpdateRequestDto;
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

    public Content update(ContentUpdateRequestDto requestDto) {
        this.name = requestDto.getName();
        return this;
    }

    public Content changePlatform(Platform platform) {
        this.platform = platform;
        return this;
    }
}