package homework.querydsl.contents24.web.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
     * @RequestBody에 @Valid로 정의된 클래스에서 유효성 검증에 실패했을 경우 발생한다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);

        //유효성 검증 실패한 필드, 에러 메시지 정보
        String errorField = e.getBindingResult().getFieldError().getField();
        String defaultMessage = e.getBindingResult().getFieldError().getDefaultMessage();

        return new ResponseEntity<>(errorField + ": " + defaultMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * PK로 조회 시 결과 값이 없을 경우 응답코드 404 반환
     * @param exception
     * @return
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundException(final RuntimeException exception) {
        log.info("error", exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * 클라이언트 입력 값이 유효하지 않을 경우 응답코드 400 반환
     * @param exception
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentException(final RuntimeException exception) {
        log.info("error", exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
