package com.ll.exam.springbatch.job.withParam;



import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
/*
@Slf4j
@Configuration
public class WithParamConfig {
    @Bean
    public Job withParamJob(JobRepository jobRepository, Step withParamStep1){
        return new JobBuilder("withParamJob",jobRepository)
                .start(withParamStep1)
                .build();
    }
    @Bean
    @JobScope // 독립적으로 실행하기 위헤 Scope 추가
    public Step withParamStep1(JobRepository jobRepository, Tasklet withParamStep1Tasklet, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("withParamStep1",jobRepository)
                .tasklet(withParamStep1Tasklet,platformTransactionManager).build();
    }

    @Bean
    @StepScope
    public Tasklet withParamStep1Tasklet(){
        return ((contribution, chunkContext) -> {
            System.out.println(">>withParamStep1");
            return RepeatStatus.FINISHED;
        });
    }
}
 */
