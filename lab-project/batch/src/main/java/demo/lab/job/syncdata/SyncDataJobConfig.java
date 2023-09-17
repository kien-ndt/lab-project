package demo.lab.job.syncdata;

import demo.lab.config.listener.BatchReportListener;
import demo.lab.db.entity.BookEntity;
import demo.lab.db.entity.DeletedBookEntity;
import demo.lab.elasticsearch.document.BookDocument;
import demo.lab.job.syncdata.step.delete.processor.SyncDeleteDataProcessor;
import demo.lab.job.syncdata.step.delete.reader.SyncDeleteDataReader;
import demo.lab.job.syncdata.step.delete.writer.SyncDeleteDataWriter;
import demo.lab.job.syncdata.step.insertupdate.processor.SyncInsertUpdateDataProcessor;
import demo.lab.job.syncdata.step.insertupdate.reader.SyncInsertUpdateDataReader;
import demo.lab.job.syncdata.step.insertupdate.writer.SyncInsertUpdateDataWriter;
import demo.lab.job.syncdata.step.updatecheckpoint.UpdateCheckpointSyncTasklet;
import demo.lab.model.SqlExecutor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SyncDataJobConfig {
    @Bean("syncDataJob")
    public Job syncDataJobBulder(JobRepository jobRepository, BatchReportListener listener,
                                 @Qualifier("syncInsertAndUpdateDataStep") Step syncInsertAndUpdateStep,
                                 @Qualifier("syncDeleteDataStep") Step syncDeleteDataStep,
                                 @Qualifier("updateCheckpointSyncStep") Step updateCheckpointSyncStep) {
        return new JobBuilder("SyncDataJob")
//                .incrementer(new RunIdIncrementer())
                .repository(jobRepository)
                .start(syncInsertAndUpdateStep)
                .next(syncDeleteDataStep)
                .next(updateCheckpointSyncStep)
                .listener(listener)
                .build();
    }

    @Bean("syncInsertAndUpdateDataStep")
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                     BatchReportListener listener,
                     SyncInsertUpdateDataReader reader,
                     SyncInsertUpdateDataProcessor processor,
                     SyncInsertUpdateDataWriter writer) {
        return new StepBuilder("SyncInsertAndUpdateDataStep")
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .<BookEntity, BookDocument>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(listener)
                .build();
    }

    @Bean("syncDeleteDataStep")
    public Step syncDeleteDataStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                   BatchReportListener listener,
                                   SyncDeleteDataReader reader,
                                   SyncDeleteDataProcessor processor,
                                   SyncDeleteDataWriter writer) {
        return new StepBuilder("SyncDeleteDataStep")
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .<DeletedBookEntity, SqlExecutor>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(listener)
                .build();
    }

    @Bean("updateCheckpointSyncStep")
    public Step updateCheckpointSyncStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                         BatchReportListener listener,
                                         UpdateCheckpointSyncTasklet updateCheckpointSyncTasklet) {
        return new StepBuilder("UpdateCheckpointSyncStep")
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .tasklet(updateCheckpointSyncTasklet)
                .listener(listener)
                .build();
    }

}
