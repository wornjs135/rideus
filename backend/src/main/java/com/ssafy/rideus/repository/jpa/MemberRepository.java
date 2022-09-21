package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.dto.rank.response.RankDistanceResponseDto;
import com.ssafy.rideus.dto.rank.response.RankDistanceResponseDtoInterface;
import com.ssafy.rideus.dto.rank.response.RankTimeResponseDtoInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

//    Optional<Member> findByEmail(String email);
//
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
//
    @Query("SELECT m.refreshToken FROM Member m WHERE m.id=:id")
    String getRefreshTokenById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Member m SET m.refreshToken=:token WHERE m.id=:id")
    void updateRefreshToken(@Param("id") Long id, @Param("token") String token);

    Optional<Member> findByKakaoId(Long kakaoId);

    @Query(value = "SELECT member_id as memberId, nickname, profile_image_url as profileImageUrl, total_time as totalTime, " +
            "RANK() OVER (ORDER BY CAST(total_time as DECIMAL(20, 0)) DESC) AS ranking FROM member", nativeQuery = true)
    List<RankTimeResponseDtoInterface> searchRankTotalTime();

    @Query(value = "SELECT member_id as memberId, nickname, profile_image_url as profileImageUrl, total_distance as totalDistance, " +
            "RANK() OVER (ORDER BY total_distance DESC) AS ranking FROM member", nativeQuery = true)
    List<RankDistanceResponseDtoInterface> searchRankTotalDistance();

    @Query(value = "SELECT member_id as memberId, nickname, profile_image_url as profileImageUrl, total_time as totalTime, " +
            "RANK() OVER (ORDER BY CAST(total_time as DECIMAL(20, 0)) DESC) AS ranking FROM member LIMIT 3", nativeQuery = true)
    List<RankTimeResponseDtoInterface> searchRankTotalTimeTop3();

    @Query(value = "SELECT member_id as memberId, nickname, profile_image_url as profileImageUrl, total_distance as totalDistance, " +
            "RANK() OVER (ORDER BY total_distance DESC) AS ranking FROM member LIMIT 3", nativeQuery = true)
    List<RankDistanceResponseDtoInterface> searchRankTotalDistanceTop3();


    @Query(value = "SELECT a.ranking " +
            "FROM (SELECT member_id, RANK() OVER (ORDER BY CAST(total_time AS DECIMAL(20, 0)) DESC) AS ranking " +
            "FROM member) a " +
            "WHERE a.member_id = :memberId", nativeQuery = true)
    Long searchMyRankTotalTime(@Param("memberId") Long memberId);

    @Query(value = "SELECT a.ranking " +
            "FROM (SELECT member_id, RANK() OVER (ORDER BY CAST(total_distance AS DECIMAL(20, 0)) DESC) AS ranking " +
            "FROM member) a " +
            "WHERE a.member_id = :memberId", nativeQuery = true)
    Long searchMyRankTotalDistance(@Param("memberId") Long memberId);

    @Query(value = "select * " +
            "FROM (SELECT member_id AS memberId, nickname, profile_image_url AS profileImageUrl, total_time AS totalTime, " +
            "RANK() OVER (ORDER BY CAST(total_time AS DECIMAL(20, 0)) DESC) AS ranking " +
            "FROM member) c " +
            "WHERE ranking " +
            "BETWEEN :myTimeRank -1 " +
            "AND :myTimeRank +1", nativeQuery = true)
    List<RankTimeResponseDtoInterface> searchRankMemberTimeWithUpAndDown(Long myTimeRank);

    @Query(value = "select * " +
            "FROM (SELECT member_id AS memberId, nickname, profile_image_url AS profileImageUrl, total_distance AS totalDistance, " +
            "RANK() OVER (ORDER BY CAST(total_distance AS DECIMAL(20, 0)) DESC) AS ranking " +
            "FROM member) c " +
            "WHERE ranking " +
            "BETWEEN :myDistanceRank -1 " +
            "AND :myDistanceRank +1", nativeQuery = true)
    List<RankDistanceResponseDtoInterface> searchRankMemberDistanceWithUpAndDown(Long myDistanceRank);
}
