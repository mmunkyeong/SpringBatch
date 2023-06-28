package com.ll.exam.springbatch.job.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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
    @Bean
    public Job simpleJob1(JobRepository jobRepository, Step simleStep1){
        return new JobBuilder("simpleJob",jobRepository)
                .incrementer(new RunIdIncrementer())// 강제로 매번 다른 ID를 실행시에 파라미터로 구현
                .start(simleStep1)
                .build();
    }

    @Bean
    public Step simpleStep1(JobRepository jobRepository, Tasklet testTasklet, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("simpleStep1",jobRepository)
                .tasklet(testTasklet,platformTransactionManager).build();
    }

    @Bean
    public Tasklet testTasklet(){
        return ((contribution, chunkContext) -> {
            System.out.println(">>step1");
            return RepeatStatus.FINISHED;
        });
    }

}
