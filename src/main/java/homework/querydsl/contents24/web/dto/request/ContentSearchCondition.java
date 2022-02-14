package homework.querydsl.contents24.web.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * 컨텐츠 검색 조건 파라미터 클래스
 */
@Getter @Setter
public class ContentSearchCondition {

    @Size(max = 200)
    @ApiParam(value = "[검색 조건] 플랫폼 이름")
    private String platformName;

    @Size(max = 200)
    @ApiParam(value = "[검색 조건] 컨텐츠 이름")
    private String contentName;

    @Min(0)
    @ApiParam(value = "조회 대상 페이지, 시작 = 0", example = "1")
    private int page;

    @ApiParam(value = "한 페이지 당 게시물 갯수", example = "50")
    private int size = 50;

    public PageRequest initPageRequest() {
        return PageRequest.of(page, size);
    }
}
