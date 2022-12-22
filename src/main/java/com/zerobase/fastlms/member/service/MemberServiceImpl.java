package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.components.MailComponents;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.exception.MemberNotEmailAuthException;
import com.zerobase.fastlms.exception.MemberStopUserException;
import com.zerobase.fastlms.member.entity.LoginHistory;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.entity.MemberCode;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.repository.LoginHistoryRepository;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.zerobase.fastlms.member.entity.MemberCode.MEMBER_STATUS_ING;
import static com.zerobase.fastlms.member.entity.MemberCode.MEMBER_STATUS_REQUEST;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;
    private final LoginHistoryRepository loginHistoryRepository;


    @Override
    public boolean register(MemberInput parameter) {
        String userEmail = parameter.getUserId();

        Optional<Member> optionalMember =
                memberRepository.findByUserId(userEmail);
        if (optionalMember.isPresent()) {
            // 현재 userId에 해당하는 데이터 존재
            return false;
        }

        String uuid = UUID.randomUUID().toString();
        String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());
        Member member = Member.builder()
                .userId(userEmail)
                .userName(parameter.getUserName())
                .phone(parameter.getPhone())
                .password(encPassword)
                .createdAt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .userStatus(MEMBER_STATUS_REQUEST)
                .build();
        memberRepository.save(member);

        String subject = "fastlms 사이트 가입을 축하드립니다.";
        String text = "<p>fastlms 사이트 가입을 축하드립니다.</p><p>아래 링크를 클릭하셔서 가입해주세요</p>"
                + "<div><a href='http://localhost:8080/member/email-auth?id=" + uuid + "'>가입완료</a></div>";
        mailComponents.sendMail(userEmail, subject, text);

        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
        if (!optionalMember.isPresent()) {
            return false;
        }

        Member member = optionalMember.get();

        if (member.isEmailAuthYn()) {
            return false;
        }

        member.setUserStatus(MEMBER_STATUS_ING);
        member.setEmailAuthYn(true);
        member.setEmailAuthTime(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }

    @Override
    public void saveLoginHistory(String userId, String clientIp, String userAgent) {
        // 로그인 성공해서 날라온 메시지이고, ip와 useragent 모두 검증해서 보낸 거라 따로 검증 필요없나?
        loginHistoryRepository.save(
                LoginHistory.of(userId, clientIp, userAgent));
    }

    @Override
    public MemberDto memberInfo(String userId) {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            return null;
        }

        Member member = optionalMember.get();

        return MemberDto.of(member);
    }

    @Override
    public ServiceResult modifyMemberInfo(MemberInput parameter) {
        String userId = parameter.getUserId();

        Optional<Member> optionalMember = memberRepository.findById(userId);
        validateMember(optionalMember);

        Member member = optionalMember.get();
        member.setPhone(parameter.getPhone());
        member.setUpdatedAt(LocalDateTime.now());
        memberRepository.save(member);

        return new ServiceResult(true);
    }

    @Override
    public ServiceResult updateMemberPassword(MemberInput parameter) {
        String userId = parameter.getUserId();

        Optional<Member> optionalMember = memberRepository.findById(userId);
        validateMember(optionalMember);

        Member member = optionalMember.get();

        if (!PasswordUtils.equals(parameter.getPassword(), member.getPassword())) {
            return new ServiceResult(false, "비밀번호가 일치하지 않습니다");
        }

        member.setPassword(PasswordUtils.encPassword(parameter.getNewPassword()));
        memberRepository.save(member);

        return new ServiceResult(true);
    }

    @Override
    public boolean sendResetPassword(ResetPasswordInput parameter) {
        Optional<Member> optionalMember =
                memberRepository.findByUserIdAndUserName(
                        parameter.getUserId(),
                        parameter.getUserName()
                );
        validateMember(optionalMember);

        Member member = optionalMember.get();

        // 비밀번호 변경할 수 있는 조건 추가하기
        String uuid = UUID.randomUUID().toString();
        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitTime(LocalDateTime.now().plusMinutes(10));
        memberRepository.save(member);

        String email = parameter.getUserId();
        String subject = "[fastlms] 비밀번호 초기화 메일입니다. ";
        String text = "<p>fastlms 초기화 메일 입니다. </p>" +
                "<p>아래 링크를 클릭하셔서 비밀번호를 초기화 해주세요.</p>" +
                "<div><a target='_blank' href='http://localhost:8080/member/reset/password?id=" + uuid + "'>비밀번호 초기화 링크</a></div>";
        mailComponents.sendMail(email, subject, text);

        return true;
    }

    @Override
    public boolean resetPassword(String uuid, String password) {
        Optional<Member> optionalMember =
                memberRepository.findByResetPasswordKey(uuid);
        validateMember(optionalMember);

        Member member = optionalMember.get();

        // 초기화 날짜가 유효한지 체크
        if (member.getResetPasswordLimitTime() == null) {
            throw new RuntimeException("유효한 기간이 아닙니다.");
        }

        if (member.getResetPasswordLimitTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("유효한 기간이 아닙니다.");
        }

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setPassword(encPassword);
        member.setResetPasswordKey("");
        member.setResetPasswordLimitTime(null);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {
        Optional<Member> optionalMember =
                memberRepository.findByResetPasswordKey(uuid);
        validateMember(optionalMember);

        Member member = optionalMember.get();

        // 초기화 날짜가 유효한지 체크
        if (member.getResetPasswordLimitTime() == null) {
            throw new RuntimeException("유효한 기간이 아닙니다.");
        }

        if (member.getResetPasswordLimitTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("유효한 기간이 아닙니다.");
        }

        return true;
    }

    @Override
    public ServiceResult withdrawMember(String userId, String password) {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        validateMember(optionalMember);

        Member member = optionalMember.get();
        if (!PasswordUtils.equals(password, member.getPassword())) {
            return new ServiceResult(false, "비밀번호가 일치하지 않습니다.");
        }

        member.setUserName("삭제회원");
        member.setPhone("");
        member.setPassword("");
        member.setCreatedAt(null);
        member.setUpdatedAt(null);
        member.setEmailAuthYn(false);
        member.setEmailAuthTime(null);
        member.setEmailAuthKey("");
        member.setResetPasswordKey("");
        member.setResetPasswordLimitTime(null);
        member.setUserStatus(MemberCode.MEMBER_STATUS_STOP);
        memberRepository.save(member);

        return new ServiceResult();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findById(username);

        validateMember(optionalMember);

        Member member = optionalMember.get();
        if (MEMBER_STATUS_REQUEST.equals(member.getUserStatus())) {
            throw new MemberNotEmailAuthException("이메일 인증 후 로그인을 해주세요");
        }

        if (MemberCode.MEMBER_STATUS_STOP.equals(member.getUserStatus())) {
            throw new MemberStopUserException("정지된 회원입니다.");
        }

        if (MemberCode.MEMBER_STATUS_WITHDRAW.equals(member.getUserStatus())) {
            throw new MemberStopUserException("탈퇴한 회원입니다.");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (member.isAdminYn()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        String userId = member.getUserId();

        return new User(userId, member.getPassword(), grantedAuthorities);
    }

    private static void validateMember(Optional<Member> optionalMember) {
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
    }
}
