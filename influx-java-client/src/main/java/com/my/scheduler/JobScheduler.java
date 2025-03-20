package com.my.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobScheduler {
    private final JobLauncher jobLauncher;
    private final Job simpleJob1;

    @Scheduled(cron = "*/10 * * * * *")
    public void runBatchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(simpleJob1, jobParameters);
            System.out.println("✅ Job 실행 결과: " + execution.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
