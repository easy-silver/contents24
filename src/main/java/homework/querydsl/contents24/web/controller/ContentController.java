package homework.querydsl.contents24.web.controller;

import homework.querydsl.contents24.service.ContentService;
import homework.querydsl.contents24.web.dto.request.ContentCreateRequest;
import homework.querydsl.contents24.web.dto.request.ContentSearchCondition;
import homework.querydsl.contents24.web.dto.request.ContentUpdateRequest;
import homework.querydsl.contents24.web.dto.response.ContentResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "컨텐츠 관련 컨트롤러")
@RequiredArgsConstructor
@RequestMapping("/contents")
@RestController
public class ContentController {

    private final ContentService service;

    /**
     * 신규 등록
     * @param requestDto
     * @return created Id
     */
    @ApiOperation(value = "컨텐츠 신규 등록",
                  notes = "새 컨텐츠를 등록합니다. 등록 후에는 등록한 컨텐츠의 컨텐츠 번호(PK)를 반환합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Long register(
            @ApiParam(value = "컨텐츠 등록을 위한 Request DTO")
            @Valid @RequestBody ContentCreateRequest requestDto) {

        return service.register(requestDto);
    }

    /**
     * 목록 조회(페이징| 검색 조건: 컨텐츠명, 플랫폼명)
     * @param condition
     * @param pageable
     * @return Page<ContentResponseDto>
     */
    @ApiOperation(value = "컨텐츠 목록 조회",
                  notes = "컨텐츠 이름 오름차순으로 정렬하여 조회합니다.\n" +
                          "검색 조건이 없을 경우 전체 조회되며 페이징 처리를 하여 보여줍니다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public Page<ContentResponse> list(
            @ApiParam(value = "플랫폼 목록 조회를 위한 Request DTO")
            @Valid ContentSearchCondition condition) {

        return service.search(condition, condition.initPageRequest());
    }

    /**
     * 계정별 목록 조회
     * @param accountNo
     * @return
     */
    @ApiOperation(value = "계정별 컨텐츠 목록 조회",
                  notes = "계정별로 보유한 컨텐츠 목록을 조회합니다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/account/{accountNo}")
    public List<ContentResponse> listByAccount(
            @ApiParam(value = "계정 번호(PK)", required = true, example = "1")
            @PathVariable Long accountNo) {

        return service.listByAccount(accountNo);
    }

    /**
     * 상세 조회
     * @param id
     * @return ContentsResponseDto
     */
    @ApiOperation(value = "컨텐츠 상세 조회",
                  notes = "컨텐츠 한건의 내용을 조회합니다.\n" +
                          "컨텐츠롤 보유한 계정 목록을 함께 조회합니다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ContentResponse detail(
            @ApiParam(value = "컨텐츠 번호(PK)", required = true, example = "1")
            @PathVariable Long id) {

        return service.detail(id);
    }

    /**
     * 수정
     * @param id
     * @param requestDto
     * @return updated Id
     */
    @ApiOperation(value = "컨텐츠 수정",
                  notes = "컨텐츠 번호로 조회하여 수정합니다.")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void update(
            @ApiParam(value = "컨텐츠 번호(PK)", required = true, example = "1")
            @PathVariable Long id,
            @ApiParam(value = "컨텐츠 수정을 위한 Request DTO")
            @Valid @RequestBody ContentUpdateRequest requestDto) {

        service.update(id, requestDto);
    }

    /**
     * 삭제
     * @param id
     * @return deleted Id
     */
    @ApiOperation(value = "컨텐츠 삭제",
                  notes = "컨텐츠 번호로 조회하여 삭제합니다.")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(
            @ApiParam(value = "컨텐츠 번호(PK)", required = true, example = "1")
            @PathVariable Long id) {

        service.delete(id);
    }
}
