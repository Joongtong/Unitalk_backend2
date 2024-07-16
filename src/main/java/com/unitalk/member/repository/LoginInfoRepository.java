package com.unitalk.member.repository;

import com.unitalk.member.entity.LoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 로그인 정보 엔티티에 대한 데이터 접근을 처리하는 리포지토리 인터페이스입니다.
 */
public interface LoginInfoRepository extends JpaRepository<LoginInfo, Long> {

    /**
     * 주어진 사용자 ID에 해당하는 로그인 정보를 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 로그인 정보 엔티티
     */
    LoginInfo findByUserUserId(Long userId);

    /**
     * 주어진 사용자 ID가 존재하는지 확인합니다.
     *
     * @param userId 사용자 ID
     * @return 사용자 ID가 존재하면 true, 그렇지 않으면 false
     */
    Boolean existsByUserUserId(Long userId);
}
