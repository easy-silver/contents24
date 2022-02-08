package homework.querydsl.contents24.controller;

import homework.querydsl.contents24.dto.ContentCreateRequestDto;
import homework.querydsl.contents24.dto.ContentSearchCondition;
import homework.querydsl.contents24.dto.ContentUpdateRequestDto;
import homework.querydsl.contents24.service.ContentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 컨텐츠 컨트롤러
 */
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
                  notes = "컨텐츠를 신규 등록합니다.")
    @PostMapping("/")
    public ResponseEntity register(ContentCreateRequestDto requestDto) {
        requestDto.checkValidation();
        return new ResponseEntity(service.register(requestDto), HttpStatus.CREATED);
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
    @GetMapping("/")
    public ResponseEntity list(ContentSearchCondition condition, Pageable pageable) {
        condition.checkValidation();
        return new ResponseEntity(service.search(condition, pageable), HttpStatus.OK);
    }

    /**
     * 계정별 목록 조회
     * @param accountNo
     * @return
     */
    @ApiOperation(value = "계정별 컨텐츠 목록 조회",
                  notes = "계정별로 보유한 컨텐츠 목록을 조회합니다.")
    @GetMapping("/account/{accountNo}")
    public ResponseEntity listByAccount(
            @ApiParam(value = "계정번호", required = true, example = "1")
            @PathVariable Long accountNo) {
        return new ResponseEntity(service.listByAccount(accountNo), HttpStatus.OK);

    }

    /**
     * 상세 조회
     * @param id
     * @return ContentsResponseDto
     */
    @ApiOperation(value = "컨텐츠 상세 조회",
                  notes = "컨텐츠 한건의 내용을 조회합니다.\n" +
                          "컨텐츠롤 보유한 계정 목록을 함께 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity detail(
            @ApiParam(value = "컨텐츠 번호(PK)", required = true, example = "1")
            @PathVariable Long id) {
        return new ResponseEntity(service.detail(id), HttpStatus.OK);
    }

    /**
     * 수정
     * @param id
     * @param requestDto
     * @return updated Id
     */
    @ApiOperation(value = "컨텐츠 수정",
                  notes = "컨텐츠 번호로 조회하여 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity update(
            @ApiParam(value = "컨텐츠 번호(PK)", required = true, example = "1")
            @PathVariable Long id,
            ContentUpdateRequestDto requestDto) {
        requestDto.checkValidation();
        return new ResponseEntity(service.update(id, requestDto), HttpStatus.OK);
    }

    /**
     * 삭제
     * @param id
     * @return deleted Id
     */
    @ApiOperation(value = "컨텐츠 삭제",
                  notes = "컨텐츠 번호로 조회하여 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(
            @ApiParam(value = "컨텐츠 번호(PK)", required = true, example = "1")
            @PathVariable Long id) {
        return new ResponseEntity(service.delete(id), HttpStatus.OK);
    }
}
