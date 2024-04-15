package com.example.todadakproject.service.member;

import com.example.todadakproject.controller.member.dto.MemberRequestDto;
import com.example.todadakproject.controller.member.dto.MemberResponseDto;
import com.example.todadakproject.repository.member.MemberRepository;
import com.example.todadakproject.repository.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponseDto findById(Long id) {
        Member member = this.memberRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. user_id = " + id));
        return new MemberResponseDto(member);
    }

    public List<Member> findAll(){
        return this.memberRepository.findAll();
    }


    /** User 수정 */
    @Transactional
    public void update(Long id, MemberRequestDto requestDto) {
        Member member = this.memberRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. user_id = " + id));
        memberRepository.save(requestDto.toEntity());
    }

    /** User 삭제 */
    @Transactional
    public void delete(Long id) {
        Member member = this.memberRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. user_id = " + id));
        this.memberRepository.delete(member);
    }
}
