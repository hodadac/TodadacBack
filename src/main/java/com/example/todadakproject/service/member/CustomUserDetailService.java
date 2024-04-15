package com.example.todadakproject.service.member;


import com.example.todadakproject.controller.member.CustomUserDetails;
import com.example.todadakproject.repository.member.MemberRepository;
import com.example.todadakproject.repository.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException{
        Member member = memberRepository.findByUserId(userId).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다. username =" + userId));
        return new CustomUserDetails(member);
    }

    public UserDetails loadUserByUserId(Long idx) throws IllegalArgumentException{
        Member member = memberRepository.findById(idx).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. userid =" + idx));
        return new CustomUserDetails(member);
    }

    public UserDetails loadUserByEmail(String email) throws IllegalArgumentException {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. email = " + email));
        return new CustomUserDetails(member);
    }


}
