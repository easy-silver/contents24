package homework.querydsl.contents24.web.dto.request;

import homework.querydsl.contents24.domain.platform.Platform;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 플랫폼 요청 관련 DTO
 */
@Getter @Setter
@ApiModel(value = "platformRequest", description = "플랫폼 등록/수정을 위한 요청 DTO")
public class PlatformRequest {

    @ApiModelProperty(value = "플랫폼 이름", required = true, example = "인프런")
    private String name;

    @ApiModelProperty(value = "플랫폼 링크", required = true, example = "https://www.inflearn.com")
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
