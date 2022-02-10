package homework.querydsl.contents24.web.dto.response;

import homework.querydsl.contents24.domain.content.Content;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@ApiModel(value = "contentResponse", description = "컨텐츠의 상세 정보를 반환하는 데이터 모델")
@NoArgsConstructor
@Getter @Setter
public class ContentResponse {

    @ApiModelProperty(position = 1, value = "컨텐츠 번호(PK)", required = true, example = "1")
    private Long id;

    @ApiModelProperty(position = 2, value = "컨텐츠 이름", required = true, example = "실전! Querydsl")
    private String name;

    @ApiModelProperty(position = 3, value = "플랫폼 이름", required = true, example = "인프런")
    private String platformName;

    @ApiModelProperty(position = 4, value = "보유한 계정 목록", example = "인프런")
    private List<String> accountList;

    public ContentResponse(Content entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.platformName = entity.getPlatform().getName();
    }
}
