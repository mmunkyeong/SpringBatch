package com.ll.exam.springbatch.job.makeReabateOrderItem;

import com.ll.exam.springbatch.app.order.entity.OrderItem;
import com.ll.exam.springbatch.app.order.entity.RebateOrderItem;
import com.ll.exam.springbatch.app.order.repository.OrderItemRepository;
import com.ll.exam.springbatch.app.order.repository.RebateOrderItemRepository;
import com.ll.exam.springbatch.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@Slf4j

public class MakeRebateOrderItemJobConfig {
    private final JobRepository jobRepository;
    private final OrderItemRepository orderItemRepository;
    private final RebateOrderItemRepository rebateOrderItemRepository;


    @Bean
    public Job makeRebateOrderItemJob(Step makeRebateOrderItemStep1, CommandLineRunner initData) throws Exception {
        initData.run();

        return new JobBuilder("makeRebateOrderItemJob",jobRepository)
                .start(makeRebateOrderItemStep1)
                .build();
    }

    @Bean
    @JobScope
    public Step makeRebateOrderItemStep1(
            PlatformTransactionManager transactionManager,
            ItemReader orderItemReader,
            ItemProcessor orderItemToRebateOrderItemProcessor,
            ItemWriter rebateOrderItemWriter
    ) {
        return new StepBuilder("makeRebateOrderItemStep1",jobRepository)
                .<OrderItem, RebateOrderItem>chunk(100,transactionManager)
                .reader(orderItemReader)
                .processor(orderItemToRebateOrderItemProcessor)
                .writer(rebateOrderItemWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<OrderItem> orderItemReader(
            @Value("#{jobParameters['month']}") String yearMonth
    ) {
        int monthEndDay = Util.date.getEndDayOf(yearMonth);
        LocalDateTime fromDate = Util.date.parse(yearMonth + "-01 00:00:00.000000");
        LocalDateTime toDate = Util.date.parse(yearMonth + "-%02d 23:59:59.999999".formatted(monthEndDay));

        return new RepositoryItemReaderBuilder<OrderItem>()
                .name("orderItemReader")
                .repository(orderItemRepository)
                .methodName("findAllByPayDateBetween")
                .pageSize(100)
                .arguments(Arrays.asList(fromDate, toDate))
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<OrderItem, RebateOrderItem> orderItemToRebateOrderItemProcessor() {
        return RebateOrderItem::new;
    }

    @StepScope
    @Bean
    public ItemWriter<RebateOrderItem> rebateOrderItemWriter() {
        return items -> items.forEach(item -> {
            RebateOrderItem oldRebateOrderItem = rebateOrderItemRepository.findByOrderItemId(item.getOrderItem().getId()).orElse(null);

            if (oldRebateOrderItem != null) {
                rebateOrderItemRepository.delete(oldRebateOrderItem);
            }
            System.out.println("Rebate실행!");
            rebateOrderItemRepository.save(item);
        });
    }
}

