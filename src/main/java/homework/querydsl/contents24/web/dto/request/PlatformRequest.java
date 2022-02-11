package homework.querydsl.contents24.web.dto.request;

import homework.querydsl.contents24.domain.platform.Platform;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 플랫폼 요청 관련 DTO
 */
@Getter @Setter
@ApiModel(value = "platformRequest", description = "플랫폼 등록/수정을 위한 요청 DTO")
public class PlatformRequest {

    @NotBlank
    @ApiModelProperty(value = "플랫폼 이름", required = true, example = "인프런")
    private String name;

    @ApiModelProperty(value = "플랫폼 링크", example = "https://www.inflearn.com")
    private String link;

    public Platform toEntity() {
        return Platform.builder()
                .name(name)
                .link(link)
                .build();
    }
}
