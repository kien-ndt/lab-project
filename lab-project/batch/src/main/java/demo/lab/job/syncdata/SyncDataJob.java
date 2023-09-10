package demo.lab.job.syncdata;

import demo.lab.config.listener.BatchReportListener;
import demo.lab.db.entity.BookEntity;
import demo.lab.elasticsearch.document.BookDocument;
import demo.lab.job.syncdata.step.processor.SyncDataJobProcessor;
import demo.lab.job.syncdata.step.reader.SyncDataJobReader;
import demo.lab.job.syncdata.step.writer.SyncDataJobWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class SyncDataJob {

    @Bean
    public Job syncDataJobBulder(JobRepository jobRepository, BatchReportListener listener, Step step1) {
        return new JobBuilder("SyncDataJob")
                .incrementer(new RunIdIncrementer())
                .repository(jobRepository)
                .start(step1)
                .listener(listener)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      BatchReportListener listener,
                      SyncDataJobReader reader,
                      SyncDataJobProcessor processor,
                      SyncDataJobWriter writer) {
        return new StepBuilder("SyncDataJobStep")
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .<BookEntity, BookDocument> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(listener)
                .build();
    }

}
