package com.ll.exam.springbatch.app.member.service;

import com.ll.exam.springbatch.app.cash.entity.CashLog;
import com.ll.exam.springbatch.app.cash.service.CashService;
import com.ll.exam.springbatch.app.member.entity.Member;
import com.ll.exam.springbatch.app.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final CashService cashService;
    @Transactional
    public Member join(String username, String password, String email) {
        Member member = Member
                .builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        memberRepository.save(member);

        return member;
    }

    @Transactional
    public long addCash(Member member, long price){
        CashLog cashLong=cashService.addCash(member,price);

        long newRestCash=member.getRestCash()+cashLong.getPrice();
        member.setRestCash(newRestCash);
        memberRepository.save(member);
        return newRestCash;
    }

    public long getRestCash(Member member){
        return member.getRestCash();
    }
}