package homework.querydsl.contents24.repository;

import homework.querydsl.contents24.entity.Content;
import homework.querydsl.contents24.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 컨텐츠 레포지토리
 */
public interface ContentRepository extends JpaRepository<Content, Long>, ContentRepositoryCustom {

    @Query("SELECT c FROM Content c ORDER BY c.id DESC")
    List<Content> findAll();

    List<Content> findByPlatform(Platform platform);

    @Modifying
    @Transactional
    @Query("DELETE FROM Content c WHERE c.platform.id = :id")
    void deleteAllByPlatform(@Param("id") Long platformNo);
}
