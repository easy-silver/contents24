package homework.querydsl.contents24.web.controller;

import homework.querydsl.contents24.service.PlatformService;
import homework.querydsl.contents24.web.dto.request.PlatformRequest;
import homework.querydsl.contents24.web.dto.request.PlatformSearchCondition;
import homework.querydsl.contents24.web.dto.response.PlatformResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(description = "플랫폼 관련 컨트롤러")
@RequiredArgsConstructor
@RequestMapping("/platforms")
@RestController
public class PlatformController {

    private final PlatformService platformService;

    /**
     * 신규 등록
     * @return 생성된 platformId
     */
    @ApiOperation(value = "플랫폼 신규 등록",
            notes = "새 플랫폼을 등록합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Long register(
            @ApiParam(value = "플랫폼 등록을 위한 Request DTO")
            @Valid @RequestBody PlatformRequest platformRequest) {

        return platformService.register(platformRequest);
    }

    /**
     * 목록 조회(검색 조건: 플랫폼 이름, 플랫폼 링크 | 페이징 처리)
     * @return Page<PlatformResponse>
     */
    @ApiOperation(value = "플랫폼 목록 조회",
                  notes = "플랫폼명 오름차순으로 정렬하여 조회합니다. \n" +
                          "검색 조건이 없을 경우 전체 조회되며 페이징 처리를 하여 보여줍니다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public Page<PlatformResponse> list(
            @ApiParam(value = "플랫폼 목록 조회를 위한 Request DTO")
            PlatformSearchCondition condition,
            Pageable pageable) {

        condition.checkValidation();
        return platformService.search(condition, pageable);
    }

    /**
     * 상세
     * 플랫폼 상세 정보 및 보유 컨텐츠 목록 반환
     * @param id
     * @return
     */
    @ApiOperation(value = "플랫폼 상세 조회",
                  notes = "플랫폼 번호(PK)로 한 건의 플랫폼을 조회합니다.\n" +
                          "플랫폼에 속한 전체 컨텐츠 목록을 함께 조회합니다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public PlatformResponse detail(
            @ApiParam(value = "플랫폼 번호(PK)", required = true, example = "1")
            @PathVariable Long id) {

        return platformService.detail(id);
    }

    @ApiOperation(value = "플랫폼 수정",
                  notes = "플랫폼 번호로 조회하여 수정합니다.")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void update(
            @ApiParam(value = "플랫폼 번호(PK)", required = true, example = "1")
            @PathVariable Long id,
            @ApiParam(value = "플랫폼 수정을 위한 Request DTO")
            @Valid @RequestBody PlatformRequest requestDto) {

        platformService.update(id, requestDto);
    }

    @ApiOperation(value = "플랫폼 삭제",
                  notes = "플랫폼 번호로 조회하여 삭제합니다.")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(
            @ApiParam(value = "플랫폼 번호(PK)", required = true, example = "1")
            @PathVariable Long id) {

        platformService.deleteById(id);
    }
}
