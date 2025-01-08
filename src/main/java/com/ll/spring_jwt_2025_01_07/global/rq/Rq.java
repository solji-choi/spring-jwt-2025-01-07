package com.ll.spring_jwt_2025_01_07.global.rq;

import com.ll.spring_jwt_2025_01_07.domain.member.member.entity.Member;
import com.ll.spring_jwt_2025_01_07.domain.member.member.service.MemberService;
import com.ll.spring_jwt_2025_01_07.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Optional;

//Request/Response 를 추상화한 객체
//Request, Response, Cookie, Sessoin 등을 다룬다.
@RequestScope
@Component
@RequiredArgsConstructor
public class Rq {
    private final MemberService memberService;

    //스프링 시큐리티가 이해하는 방식으로 강제 로그인 처리
    //임시 방편
    public void setLogin(Member member) {
        UserDetails user = new SecurityUser(
                member.getId(),
                member.getUsername(),
                "",
                List.of()
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword(),
                user.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public Member getActor() {
        return Optional.ofNullable(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        )
                .map(Authentication::getPrincipal)
                .filter(principal -> principal instanceof SecurityUser)
                .map(principal -> (SecurityUser) principal)
                .map(securityUser -> new Member(securityUser.getId(), securityUser.getUsername()))
                .orElse(null);
    }
}
