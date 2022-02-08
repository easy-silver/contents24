package homework.querydsl.contents24.service;

import homework.querydsl.contents24.dto.ContentResponseDto;
import homework.querydsl.contents24.dto.PlatformRequestDto;
import homework.querydsl.contents24.dto.PlatformResponseDto;
import homework.querydsl.contents24.dto.PlatformSearchCondition;
import homework.querydsl.contents24.entity.Platform;
import homework.querydsl.contents24.repository.AccountRepository;
import homework.querydsl.contents24.repository.ContentRepository;
import homework.querydsl.contents24.repository.PlatformRepository;
import homework.querydsl.contents24.repository.PossessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PlatformService {

    private final PlatformRepository repository;
    private final ContentRepository contentRepository;
    private final AccountRepository accountRepository;
    private final PossessionRepository possessionRepository;

    /**
     * 플랫폼 신규 등록
     * @param requestDto
     * @return
     */
    @Transactional
    public Long register(PlatformRequestDto requestDto) {
        return repository.save(requestDto.toEntity()).getId();
    }

    /**
     * 플랫폼 검색 조회(플랫폼명, 링크)
     * @param condition
     * @param pageable
     * @return
     */
    public Page<PlatformResponseDto> search(PlatformSearchCondition condition, Pageable pageable) {
        return repository.search(condition, pageable);
    }

    /**
     * 플랫폼 전체 조회
     * 조회해 온 엔티티를 DTO로 변환하여 리스트 반환
     */
    public List<PlatformResponseDto> findAll() {
        return repository.findAll().stream()
                .map(PlatformResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 플랫폼 단건 상세 조회
     * @param id
     * @return responseDto
     */
    public PlatformResponseDto detail(Long id) {
        Platform platform = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 플랫폼입니다. platformNo=" + id));

        PlatformResponseDto platformResponseDto = new PlatformResponseDto(platform);

        //해당 플랫폼 소속 전체 컨텐츠 목록 조회
        platformResponseDto.setContentsList(contentRepository.findByPlatform(platform)
                .stream().map(ContentResponseDto::new).collect(Collectors.toList()));

        return platformResponseDto;
    }

    /**
     * 플랫폼 수정
     * @param id
     * @param requestDto
     * @return id
     */
    @Transactional
    public Long update(Long id, PlatformRequestDto requestDto) {
        Platform platform = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 플랫폼입니다. platformNo=" + id));

        if(requestDto != null)
            repository.save(platform.update(requestDto));

        return platform.getId();
    }

    /**
     * 플랫폼 단건 삭제(PK)
     * @param id
     * @return id
     */
    @Transactional
    public Long deleteById(Long id) {
        Long platformNo = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 플랫폼입니다. platformNo=" + id)).getId();

        // 1.플랫폼에 해당하는 계정 번호 확인(리스트)
        List<Long> ids = accountRepository.findByPlatform(platformNo);

        // 2.계정 번호 리스트로 보유 데이터 삭제
        possessionRepository.deleteAllByAccount(ids);

        // 3.계정 삭제(플랫폼 번호로 삭제)
        accountRepository.deleteAllByPlatform(platformNo);

        // 4.플랫폼 번호로 컨텐츠 삭제
        contentRepository.deleteAllByPlatform(platformNo);

        // 5.플랫폼 삭제
        repository.deleteById(platformNo);

        return platformNo;
    }
}
