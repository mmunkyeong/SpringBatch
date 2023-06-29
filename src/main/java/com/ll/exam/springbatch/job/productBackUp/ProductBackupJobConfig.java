package com.ll.exam.springbatch.job.productBackUp;


import lombok.RequiredArgsConstructor;
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
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ProductBackupJobConfig {
    @Bean
    public Job productBackupJob(JobRepository jobRepository, Step productBackupStep1, CommandLineRunner initData) throws Exception {
        initData.run();

        return new JobBuilder("productBackupJob",jobRepository)
                .start(productBackupStep1)
                .build();
    }

    @Bean
    @JobScope
    public Step productBackupStep1(JobRepository jobRepository,Tasklet productBackupStep1Tasklet, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("productBackupStep1",jobRepository)
                .tasklet(productBackupStep1Tasklet,platformTransactionManager).build();
    }

    @Bean
    @StepScope
    public Tasklet productBackupStep1Tasklet() {
        return (contribution, chunkContext) -> {
            log.debug("productBackupStep1Tasklet 실행됨!");

            return RepeatStatus.FINISHED;
        };
    }
}