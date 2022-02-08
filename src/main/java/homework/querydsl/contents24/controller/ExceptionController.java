package homework.querydsl.contents24.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

/**
 * 공통 예외처리 클래스
 */
@Slf4j
@ControllerAdvice
public class ExceptionController {

    /**
     * PK로 조회 시 결과 값이 없을 경우 응답코드 404 반환
     * @param exception
     * @return
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity entityNotFoundException(final RuntimeException exception) {
        log.info("error", exception);
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    /**
     * 클라이언트 입력 값이 유효하지 않을 경우 응답코드 400 반환
     * @param exception
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalArgumentException(final RuntimeException exception) {
        log.info("error", exception);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
