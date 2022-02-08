package homework.querydsl.contents24.repository;

import homework.querydsl.contents24.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 플랫폼 레포지토리
 */
public interface PlatformRepository extends JpaRepository<Platform, Long>, PlatformRepositoryCustom {

    @Query("SELECT p FROM Platform p ORDER BY p.id DESC")
    List<Platform> findAll();
}
