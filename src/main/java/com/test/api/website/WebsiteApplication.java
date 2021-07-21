package com.test.api.website;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebsiteApplication implements CommandLineRunner {
    private final static String newline = "\n";

    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(WebsiteApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (productRepository.count() == 0) {
            System.out.println("Record Creating...");
            productRepository.save(new Product("face mask", "FM-HKTV01", 100, "tko"));
            productRepository.save(new Product("face mask", "FM-HKTV01", 300, "kt"));
            productRepository.save(new Product("figure", "FM-HKTV02", 180, "ts T"));
            productRepository.save(new Product("mobile", "FM-HKTV06", 50, "T ko"));
            productRepository.save(new Product("clothes", "FM-HKTV05", 100, "CWB"));
            productRepository.save(new Product("face mask", "FM-HKTV01", 180, "CWB"));
            productRepository.save(new Product("pen", "FM-HKTV03", 80, "C W B"));
        } else {
            System.out.println("Record Existed!");
        }
    }
}
