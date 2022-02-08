package homework.querydsl.contents24.controller;

import homework.querydsl.contents24.dto.PlatformRequestDto;
import homework.querydsl.contents24.dto.PlatformSearchCondition;
import homework.querydsl.contents24.service.PlatformService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 플랫폼 컨트롤러
 */
@RequiredArgsConstructor
@RequestMapping("/platforms")
@RestController
public class PlatformController {

    private final PlatformService service;

    /**
     * 신규 등록
     * @param requestDto
     * @return
     */
    @ApiOperation(value = "플랫폼 신규 등록 ",
                  notes = "플랫폼을 새롭게 등록합니다.")
    @PostMapping("/")
    public ResponseEntity register(PlatformRequestDto requestDto) {
        requestDto.checkValidation();
        return new ResponseEntity(service.register(requestDto), HttpStatus.CREATED);
    }

    /**
     * 목록 조회(검색 조건: 플랫폼 이름, 플랫폼 링크 | 페이징 처리)
     * @param condition
     * @param pageable
     * @return
     */
    @ApiOperation(value = "플랫폼 목록 조회",
                  notes = "플랫폼명 오름차순으로 정렬하여 조회합니다. \n" +
                          "검색 조건이 없을 경우 전체 조회되며 페이징 처리를 하여 보여줍니다.")
    @GetMapping("/")
    public ResponseEntity list(PlatformSearchCondition condition, Pageable pageable) {
        condition.checkValidation();
        return new ResponseEntity(service.search(condition, pageable), HttpStatus.OK);
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
    @GetMapping("/{id}")
    public ResponseEntity detail(
            @ApiParam(value = "플랫폼 번호(PK)", required = true, example = "1")
            @PathVariable Long id) {
        return new ResponseEntity(service.detail(id), HttpStatus.OK);
    }

    /**
     * 수정
     * @param id
     * @param requestDto
     * @return
     */
    @ApiOperation(value = "플랫폼 수정",
                  notes = "플랫폼 번호로 조회하여 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity update(
            @ApiParam(value = "플랫폼 번호(PK)", required = true, example = "1")
            @PathVariable Long id, PlatformRequestDto requestDto) {
        requestDto.checkValidation();
        return new ResponseEntity(service.update(id, requestDto), HttpStatus.OK);
    }

    /**
     * 삭제
     * @param id
     * @return
     */
    @ApiOperation(value = "플랫폼 삭제",
                  notes = "플랫폼 번호로 조회하여 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(
            @ApiParam(value = "플랫폼 번호(PK)", required = true, example = "1")
            @PathVariable Long id) {
        return new ResponseEntity(service.deleteById(id), HttpStatus.OK);
    }
}
