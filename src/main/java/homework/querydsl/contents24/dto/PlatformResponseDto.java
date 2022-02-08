package homework.querydsl.contents24.dto;

import homework.querydsl.contents24.entity.Platform;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class PlatformResponseDto {

    private Long id;
    private String name;
    private String link;
    //소속 컨텐츠 리스트
    private List<ContentResponseDto> contentsList = new ArrayList<>();

    public PlatformResponseDto(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public PlatformResponseDto(Platform entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.link = entity.getLink();
    }
}
