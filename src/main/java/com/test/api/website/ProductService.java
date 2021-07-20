package com.test.api.website;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RestController
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add")
    public String addProduct(@RequestParam String name, @RequestParam String code, @RequestParam Integer weight, @RequestParam String location) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setCode(code);
        product.setWeight(weight);
        product.setLocation(location.toUpperCase());

        if(chkExistProduct(product)) {
            System.out.println("Update Successfully!");
        } else {
            productRepository.save(product);
            System.out.println(String.format("Record Created! ------> [%s, %s, %d, %s]", name, code, weight, location));
        }

        return "<form action=\"/\">" +
                "<h1>Added new product to repo!</h1>" +
                "<div><button>Back To Home Page</button></div>" +
                "</form>";
    }

    public Boolean chkExistProduct(Product product){
        List<Product> arr;

        arr = findProducts();

        System.out.println("Get Data: ---------->  ");
        for(Product p : arr) {
            if(p.getCode().equalsIgnoreCase(product.getCode())){
                System.out.println(String.format("Record with code[%s, %s] has existed", p.getName(), p.getCode()));

                if(p.getLocation().equalsIgnoreCase(product.getLocation())){

                } else {
                    productRepository.save(product);
                    System.out.println(String.format("Record Created! ------> [%s, %s, %d, %s]", product.getName(), product.getCode(), product.getWeight(), product.getLocation()));
                }

                return true;
            }
        }

        return false;
    }

    @GetMapping("/list")
    public String getProducts(){
        List<Product> products = productRepository.findAll();
        String table = "<h2>Products Table</h2><table style='text-align: left; border-collapse: collapse; border: 1px solid black'><tr style='background-color: CadetBlue'><th style='border: 1px solid black; padding: 10px'>Name</th><th style='border: 1px solid black; padding: 10px'>Code</th><th style='border: 1px solid black; padding: 10px'>Quantity</th><th style='border: 1px solid black; padding: 10px'>Location</th></tr>";

        if (products.stream().count() != 0) {
            System.out.println("Print All Records!");
            for(Product p : products){
                table += "<tr><td style='border: 1px solid black; padding: 10px'>"+p.getName()+"</td><td style='border: 1px solid black; padding: 10px'>"+p.getCode()+"</td><td style='border: 1px solid black; padding: 10px'>"+p.getWeight()+"</td><td style='border: 1px solid black; padding: 10px'>"+p.getLocation()+"</td></tr>";
            }
        }

        table += "</table>";

        table += "<br><a href=\"/\">" +
                "<button>Back To Home Page</button>" +
                "</a>";
        return table;
    }

    public List<Product> findProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/save/product")
    public String saveStorageToCSV() {
        System.out.println("Starting....");

        List<Product> products = findProducts();

        List<String[]> list = new ArrayList<>();
        File file = new File("/Users/Jimmy/Desktop/output.csv");

        // Header column value
        String[] header = { "ID", "Name", "Code", "Weight", "Location" };

        list.add(header);

        for(Product p : products) {
            String[] record = {p.getId().toString(), p.getName(), p.getCode(), p.getWeight().toString(), p.getLocation()};

            list.add(record);
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            writer.writeAll(list);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }

        System.out.println("Done...");

        return "<form action=\"/\">" +
                "<h1>Have Saved the Product Data As CSV File!</h1>" +
                "<div><button>Back To Home Page</button></div>" +
                "</form>";
    }

    @GetMapping("/save/location")
    public String saveAllLocationOfSameProduct(@RequestParam String name, @RequestParam String code){
        System.out.println("Start...");

        List<Product> products = findProducts();

        List<String[]> list = new ArrayList<>();
        File file = new File("/Users/Jimmy/Desktop/output_location.csv");

        // Header column value
        String[] header = { "ID", "Name", "Code", "Weight", "Location" };

        list.add(header);

        for(Product p : products){
            String[] record = {p.getId().toString(), p.getName(), p.getCode(), p.getWeight().toString(), p.getLocation()};
            if (p.getName().equalsIgnoreCase(name) && p.getCode().equalsIgnoreCase(code)){
                System.out.println("Find record!");
                System.out.println(String.format("[%s, %s, %d, %s]", p.getName(), p.getCode(), p.getWeight(), p.getLocation()));

                list.add(record);
            }
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            writer.writeAll(list);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }

        System.out.println("Done...");

        return "<form action=\"/\">" +
                "<h1>Have Saved All Location As CSV File!</h1>" +
                "<div><button>Back To Home Page</button></div>" +
                "</form>";
    }

    @GetMapping("/find/{code}")
    public List<Product> findProductsByCode(@PathVariable String code) {
        return productRepository.findProductsByCode(code);
    }
}
