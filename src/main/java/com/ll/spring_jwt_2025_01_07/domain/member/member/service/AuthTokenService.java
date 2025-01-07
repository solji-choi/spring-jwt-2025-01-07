package com.ll.spring_jwt_2025_01_07.domain.member.member.service;

import com.ll.spring_jwt_2025_01_07.domain.member.member.entity.Member;
import com.ll.spring_jwt_2025_01_07.standard.util.Ut;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthTokenService {
    public String getAccessToken(Member member) {
        long id = member.getId();
        String username = member.getUsername();

        return Ut.jwt.toString(
                "abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890",
                60 * 60 * 24 * 365,
                Map.of("id", id, "username", username)
        );
    }
}
