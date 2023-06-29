package com.ll.exam.springbatch.app.cash.repository;

import com.ll.exam.springbatch.app.cash.entity.CashLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashLogRepository extends JpaRepository<CashLog, Long> {
}
