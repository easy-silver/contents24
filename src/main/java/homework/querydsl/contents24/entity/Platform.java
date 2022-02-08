package homework.querydsl.contents24.entity;

import homework.querydsl.contents24.dto.PlatformRequestDto;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@ToString(of = {"id", "name", "link"})
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Platform {

    // 플랫폼 번호(PK)
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "platform_no")
    private Long id;

    // 플랫폼 이름
    @Column(name = "platform_name", nullable = false, length = 150)
    private String name;

    // 플랫폼 링크
    @Column(name = "platform_link", nullable = false)
    private String link;

    @Builder
    public Platform(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public Platform update(PlatformRequestDto requestDto) {
        this.name = requestDto.getName();
        this.link = requestDto.getLink();

        return this;
    }

}