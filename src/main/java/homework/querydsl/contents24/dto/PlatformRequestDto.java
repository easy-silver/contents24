package homework.querydsl.contents24.dto;

import homework.querydsl.contents24.entity.Platform;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

/**
 * 플랫폼 요청 관련 DTO
 */
@Getter @Setter
public class PlatformRequestDto {

    @ApiParam(value = "플랫폼 이름", required = true, example = "노마드코더")
    private String name;

    @ApiParam(value = "플랫폼 링크", required = true, example = "https://nomadcoders.co/")
    private String link;

    public Platform toEntity() {
        return Platform.builder()
                .name(name)
                .link(link)
                .build();
    }

    /* 신규 등록 입력 값 유효성 검증 */
    public void checkValidation() {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("플랫폼 이름 값이 입력되지 않았습니다.");

        if(link == null || link.isBlank())
            throw new IllegalArgumentException("플랫폼 링크 값이 입력되지 않았습니다.");
    }
}
