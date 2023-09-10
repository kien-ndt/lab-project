package demo.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchApp {
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(BatchApp.class, args)));
    }
}
