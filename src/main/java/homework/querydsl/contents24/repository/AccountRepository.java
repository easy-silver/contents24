package homework.querydsl.contents24.repository;

import homework.querydsl.contents24.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 계정 레포지토리
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    /* 플랫폼에 해당하는 가입 계정 조회 */
    @Query("SELECT a.id FROM Account a WHERE a.platform.id = :id")
    List<Long> findByPlatform(@Param("id") Long platformNo);

    /* 플랫폼에 해당하는 계정 전체 삭제 */
    @Modifying
    @Transactional
    @Query("DELETE FROM Account a WHERE a.platform.id = :platformNo")
    void deleteAllByPlatform(@Param("platformNo") Long platformNo);

}
