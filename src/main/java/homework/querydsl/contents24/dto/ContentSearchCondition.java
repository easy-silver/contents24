package homework.querydsl.contents24.dto;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

/**
 * 컨텐츠 검색 조건 파라미터 클래스
 */
@Getter @Setter
public class ContentSearchCondition {

    @ApiParam(value = "[검색 조건] 플랫폼 이름")
    private String platformName;
    @ApiParam(value = "[검색 조건] 컨텐츠 이름")
    private String contentName;

    public static final int PLATFORM_NAME_MAX_SIZE = 200;
    public static final int CONTENT_NAME_MAX_SIZE = 200;

    /* 입력 값 유효성 검증 메서드 */
    public void checkValidation() {
        if (platformName != null && platformName.length() > PLATFORM_NAME_MAX_SIZE)
            throw new IllegalArgumentException("플랫폼명 최대 글자수를 초과하였습니다.");

        if(contentName != null && contentName.length() > CONTENT_NAME_MAX_SIZE)
            throw new IllegalArgumentException("컨텐츠명 최대 글자수를 초과하였습니다.");
    }
}
