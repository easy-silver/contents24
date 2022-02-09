package homework.querydsl.contents24.web.dto;

import homework.querydsl.contents24.domain.content.Content;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class ContentResponseDto {

    private Long id;
    private String name;
    private String platformName;
    private List<String> accountList;

    public ContentResponseDto(Content entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.platformName = entity.getPlatform().getName();
    }
}
