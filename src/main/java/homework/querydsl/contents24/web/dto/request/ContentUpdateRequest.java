package homework.querydsl.contents24.web.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class ContentUpdateRequest {

    @NotBlank
    @ApiParam(value = "컨텐츠 이름", required = true, example = "수정된 컨텐츠")
    private String name;

}
