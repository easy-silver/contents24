package homework.querydsl.contents24.web.dto.response;

import homework.querydsl.contents24.domain.platform.Platform;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "platformResponse", description = "플랫폼 상세 정보를 반환하는 데이터 모델")
@NoArgsConstructor
@Getter @Setter
public class PlatformResponse {

    @ApiModelProperty(position = 1, value = "플랫폼 번호(PK)", required = true, example = "1")
    private Long id;

    @ApiModelProperty(position = 2, value = "플랫폼 이름", required = true, example = "인프런")
    private String name;

    @ApiModelProperty(position = 3, value = "플랫폼 링크", example = "https://inflearn.com")
    private String link;

    @ApiModelProperty(position = 4, value = "소속 컨텐츠 리스트")
    private List<ContentResponse> contentsList = new ArrayList<>();

    public PlatformResponse(Platform entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.link = entity.getLink();
    }
}
