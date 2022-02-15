package homework.querydsl.contents24.web.dto.request;

import homework.querydsl.contents24.domain.content.Content;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@ApiModel(value = "contentCreateRequest", description = "컨텐츠 등록을 위한 요청 데이터 모델")
public class ContentCreateRequest {

    @NotNull
    @ApiModelProperty(position = 1, value = "플랫폼 번호", required = true, example = "1")
    private Long platformNo;

    @NotBlank
    @ApiModelProperty(position = 2, value = "컨텐츠 이름", required = true, example = "TDD로 배우는 NodeJS")
    private String name;

    public Content toEntity() {
        return Content.builder()
                .name(name)
                .build();
    }

}
