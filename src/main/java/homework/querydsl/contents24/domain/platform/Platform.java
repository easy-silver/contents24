package homework.querydsl.contents24.domain.platform;

import homework.querydsl.contents24.web.dto.request.PlatformRequest;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@ToString(of = {"id", "name", "link"})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Platform {

    //플랫폼 아이디(PK)
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "platform_no")
    private Long id;

    //플랫폼 이름
    @Column(name = "platform_name", nullable = false, length = 300)
    private String name;

    //플랫폼 링크
    @Column(name = "platform_link", nullable = false)
    private String link;

    @Builder
    public Platform(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public void update(String name, String link) {
        this.name = name;
        this.link = link;
    }

}