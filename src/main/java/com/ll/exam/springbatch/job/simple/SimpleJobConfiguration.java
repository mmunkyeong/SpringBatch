package com.ll.exam.springbatch.job.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class SimpleJobConfiguration {
    @Bean // 기본 싱글톤 : 스프링부트 앱이 꺼지기 전까지 살아있음
    public Job simpleJob1(JobRepository jobRepository, Step simpleStep1, Step simpleStep2){
        return new JobBuilder("simpleJob",jobRepository)
                .incrementer(new RunIdIncrementer())// 강제로 매번 다른 ID를 실행시에 파라미터로 구현
                .start(simpleStep1)
                .next(simpleStep2) // 위에꺼 다 실행된 후에 실행
                .build();
    }

    @Bean
    @JobScope // 독립적으로 실행하기 위헤 Scope 추가
    public Step simpleStep1(JobRepository jobRepository, Tasklet testStep1Tasklet, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("simpleStep1",jobRepository)
                .tasklet(testStep1Tasklet,platformTransactionManager).build();
    }

    @Bean
    @StepScope
    public Tasklet testStep1Tasklet(){
        return ((contribution, chunkContext) -> {
            System.out.println(">>step1");
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    @JobScope
    public Step simpleStep2(JobRepository jobRepository, Tasklet testStep2Tasklet, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("simpleStep2",jobRepository)
                .tasklet(testStep2Tasklet,platformTransactionManager).build();
    }

    @Bean
    @StepScope
    public Tasklet testStep2Tasklet(){
        return ((contribution, chunkContext) -> {
            System.out.println(">>step2");
            return RepeatStatus.FINISHED;
        });
    }
}
