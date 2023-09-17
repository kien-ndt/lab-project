package demo.lab.job.syncdata.step.updatecheckpoint;

import demo.lab.elasticsearch.document.ManageUpdateDocument;
import demo.lab.elasticsearch.repository.ManageUpdateElasticSearchRepository;
import demo.lab.model.ManageDocumentEnum;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@StepScope
@Service
public class UpdateCheckpointSyncTasklet implements Tasklet {

    @Value("#{jobParameters['ExecuteTime']}")
    private Long executeTime;

    @Autowired
    private ManageUpdateElasticSearchRepository manageUpdateElasticSearchRepository;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        ManageUpdateDocument manageUpdateDocument =
                manageUpdateElasticSearchRepository.findById(ManageDocumentEnum.BOOK.id).orElse(new ManageUpdateDocument());
        manageUpdateDocument.id = ManageDocumentEnum.BOOK.id;
        manageUpdateDocument.lastUpdatedAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(executeTime),
                TimeZone.getDefault().toZoneId());

        manageUpdateElasticSearchRepository.save(manageUpdateDocument);

        return RepeatStatus.FINISHED;
    }
}
