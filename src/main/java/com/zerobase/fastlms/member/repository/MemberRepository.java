package com.zerobase.fastlms.member.repository;

import com.zerobase.fastlms.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByUserId(String userId);

    Optional<Member> findByUserName(String userName);

    Optional<Member> findByEmailAuthKey(String uuid);

    Optional<Member> findByUserIdAndUserName(String userId, String userName);

    Optional<Member> findByResetPasswordKey(String resetPasswordKey);





}
