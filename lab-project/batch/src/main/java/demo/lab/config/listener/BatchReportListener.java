package demo.lab.config.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BatchReportListener implements StepExecutionListener, JobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchReportListener.class);
    private Map<String, Map<String, String>> reportAttributes;

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info(stepExecution.getStepName());
        Map<String, String> reportAttr = new HashMap<>();
        reportAttr.put("Read count: ", String.valueOf(stepExecution.getReadCount()));
        reportAttr.put("Write count: ", String.valueOf(stepExecution.getWriteCount()));
        reportAttributes.put(stepExecution.getStepName(), reportAttr);
        return null;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        reportAttributes = new HashMap<>();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        reportAttributes.entrySet().forEach(stringMapEntry -> {
            builder.append("Step name: ").append(stringMapEntry.getKey()).append("\n");
            stringMapEntry.getValue().entrySet().forEach(stringStringEntry ->
                    builder.append(stringStringEntry.getKey()).append(stringStringEntry.getValue()).append("\n")
            );
        });
        LOGGER.info(builder.toString());
    }
}
