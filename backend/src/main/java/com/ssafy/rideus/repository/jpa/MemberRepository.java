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
}
