package demo.lab;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class BatchApp {
    public static void main(String[] args) {
        SpringApplication.run(BatchApp.class, args);
//        System.exit(SpringApplication.exit(SpringApplication.run(BatchApp.class, args)));
    }
}
