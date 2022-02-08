package homework.querydsl.contents24.dto;

import homework.querydsl.contents24.entity.Content;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ContentUpdateRequestDto {

    @ApiParam(value = "컨텐츠 이름", required = true, example = "수정된 컨텐츠")
    private String name;

    public Content toEntity() {
        return Content.builder()
                .name(name)
                .build();
    }

    /* 입력 값 유효성 검증 */
    public void checkValidation() {
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException("컨텐츠명 입력 값이 없습니다.");
    }
}
