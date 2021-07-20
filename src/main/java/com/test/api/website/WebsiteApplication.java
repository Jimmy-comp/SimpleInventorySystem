package com.test.api.website;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableAutoConfiguration
@SpringBootApplication
public class WebsiteApplication implements CommandLineRunner {
    private final static String newline = "\n";

    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        // Replace the uri string with your MongoDB deployment's connection string
//        String uri = "mongodb+srv://test:GGKqvwxbPB2citI5@cluster0.ikhkv.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
//        String uri = "mongodb+srv://test:test_123@cluster0.ikhkv.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";

//        if (args.length > 0) {
//
//            MongoOperations mongoOps = new MongoTemplate(MongoClients.create(uri), "myFirstDatabase");
//            Product product = mongoOps.findOne(new Query(), Product.class);
//
//            System.out.println("Test Start.....");
//
//            if (product == null) {
//                mongoOps.insert(new Product("face mask", "FM-HKTV01", 100, "tko"));
//                mongoOps.insert(new Product("face mask", "FM-HKTV01", 300, "kt"));
//                mongoOps.insert(new Product("figure", "FM-HKTV02", 150, "ts T"));
//                mongoOps.insert(new Product("mobile", "FM-HKTV06", 50, "T ko"));
//                mongoOps.insert(new Product("clothes", "FM-HKTV05", 100, "CWB"));
//            } else {
//                System.out.println("Existing Record...");
//            }
//            System.out.println("Test End.....");
//        }

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
        } else {
            System.out.println("Record Existed!");
        }
    }
}
