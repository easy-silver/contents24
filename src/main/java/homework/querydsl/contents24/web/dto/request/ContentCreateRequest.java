package homework.querydsl.contents24.web.dto.request;

import homework.querydsl.contents24.domain.content.Content;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class ContentCreateRequest {

    @NotNull
    @ApiParam(value = "플랫폼 번호", required = true, example = "1")
    private Long platformNo;

    @NotBlank
    @ApiParam(value = "컨텐츠 이름", required = true, example = "TDD로 배우는 NodeJS")
    private String name;

    public Content toEntity() {
        return Content.builder()
                .name(name)
                .build();
    }

}
