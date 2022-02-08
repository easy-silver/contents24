package homework.querydsl.contents24.repository;

import homework.querydsl.contents24.entity.Possession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PossessionRepository extends JpaRepository<Possession, Long>, PossessionRepositoryCustom {

    /**
     * 계정에 해당하는 보유 데이터 삭제
     * @param accountIds
     */
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Possession p WHERE p.account.id IN :ids")
    void deleteAllByAccount(@Param("ids") List<Long> accountIds);

    /**
     * 컨텐츠에 해당하는 보유 데이터 삭제
     * @param contentNo
     */
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Possession p WHERE p.content.id = :id")
    void deleteAllByContent(@Param("id") Long contentNo);
}
