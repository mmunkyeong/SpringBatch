package com.ll.exam.springbatch.app.cash.service;


import com.ll.exam.springbatch.app.cash.entity.CashLog;
import com.ll.exam.springbatch.app.cash.repository.CashLogRepository;
import com.ll.exam.springbatch.app.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashService {
    private final CashLogRepository cashLogRepository;

    public CashLog addCash(Member member, long price) {
        CashLog cashLog = CashLog.builder()
                .member(member)
                .price(price)
                .build();

        cashLogRepository.save(cashLog);

        return cashLog;
    }
}