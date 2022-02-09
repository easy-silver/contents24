package homework.querydsl.contents24.web.dto.response;

import homework.querydsl.contents24.domain.platform.Platform;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class PlatformResponse {

    private Long id;
    private String name;
    private String link;
    //소속 컨텐츠 리스트
    private List<ContentResponse> contentsList = new ArrayList<>();

    public PlatformResponse(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public PlatformResponse(Platform entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.link = entity.getLink();
    }
}
