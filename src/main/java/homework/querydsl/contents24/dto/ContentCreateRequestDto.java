package homework.querydsl.contents24.dto;

import homework.querydsl.contents24.entity.Content;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ContentCreateRequestDto {

    @ApiParam(value = "플랫폼 번호", required = true, example = "1")
    private Long platformNo;
    @ApiParam(value = "컨텐츠 이름", required = true, example = "TDD로 배우는 NodeJS")
    private String name;

    public Content toEntity() {
        return Content.builder()
                .name(name)
                .build();
    }

    /* 입력 값 유효성 검증 */
    public void checkValidation() {
        if(platformNo == null)
            throw new IllegalArgumentException("플랫폼 번호 값이 없습니다.");

        if(name == null || name.isEmpty())
            throw new IllegalArgumentException("컨텐츠명 입력 값이 없습니다.");
    }

}
