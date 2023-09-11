package demo.lab.config.schedule;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleManageRegister {

    @Autowired
    @Qualifier("syncDataJob")
    private Job syncdataJob;
    @Autowired
    private JobLauncher jobLauncher;

    @Scheduled(cron = "*/5 * * * * ?")
    public void syncdataJobPerform() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addString("JobRunTimeID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(syncdataJob, params);
    }

}
