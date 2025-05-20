package com.codesnippet.SpringBatchDemo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;

@EnableScheduling // Enable scheduled tasks
@Component
public class BatchJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job job;

    public BatchJobScheduler(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @Scheduled(cron = "0 0 * * * ?") // Runs every 5 minutes
    public void runJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startAt", new Date().getTime())
                    .toJobParameters();

            jobLauncher.run(job, jobParameters);
            System.out.println("✅ Batch job executed successfully.");
        } catch (Exception e) {
            System.err.println("⛔ Error executing batch job: " + e.getMessage());
        }
    }
}
