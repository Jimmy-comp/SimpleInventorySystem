package com.test.api.website;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

@Service
@RestController
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/form/add")
    public String addForm(){
        String html = "<form action=\"/form/add\" method=\"POST\">" +
                "<p>Let's add a product.</p>" +
                "<div>" +
                "<label for=\"name\">Product name:</label>" +
                "<input name=\"name\" id=\"name\" value=\"\">" +
                "</div>" +
                "<br>" +
                "<div>" +
                "<label for=\"code\">Product code:</label>" +
                "<input name=\"code\" id=\"code\" value=\"FM-HKTV01\">" +
                "</div>" +
                "<br>" +
                "<div>" +
                "<label for=\"weight\">Product weight:</label>" +
                "<input name=\"weight\" id=\"weight\" value=0>" +
                "</div>" +
                "<br>" +
                "<div>" +
                "<label for=\"location\">Location:</label>" +
                "<input name=\"location\" id=\"location\" value=\"TKO\">" +
                "<p>(Just enter the initials of the place)</p>" +
                "</div>" +
                "<div>" +
                "<button>Add Product</button>" +
                "</div>" +
                "</form>";

        return html;
    }

    @PostMapping("/form/add")
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
                    p.setName(product.getName());
                    p.setCode(product.getCode());
                    p.setWeight(product.getWeight());
                    p.setLocation(product.getLocation());

                    productRepository.save(p);
                    System.out.println(String.format("Same Record [%s, %s, %d, %s]", product.getName(), product.getCode(), product.getWeight(), product.getLocation()));

                    return true;
                } else {
                    productRepository.save(product);
                    System.out.println(String.format("Record Created! ------> [%s, %s, %d, %s]", product.getName(), product.getCode(), product.getWeight(), product.getLocation()));
                }
            }
        }

        return false;
    }

    @GetMapping("/list")
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
        String[] header = { "ID", "Name", "Code", "Quantity", "Location" };

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
    public String saveAllLocationOfSameProduct(@RequestParam String code){
        System.out.println("Start...");

        List<Product> products = findProducts();

        List<String[]> list = new ArrayList<>();
        File file = new File("/Users/Jimmy/Desktop/output_location.csv");

        // Header column value
        String[] header = { "ID", "Name", "Code", "Quantity", "Location" };

        list.add(header);

        for(Product p : products){
            String[] record = {p.getId().toString(), p.getName(), p.getCode(), p.getWeight().toString(), p.getLocation()};
            if (p.getCode().equalsIgnoreCase(code)){
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

    @RequestMapping("/find")
    public List<Product> findProductsByCode(@RequestParam String code) {
        return productRepository.findProductsByCodeOrderByWeight(code);
    }

    @RequestMapping("/update")
    public String update(@RequestParam String id, @RequestParam Integer weight, @RequestParam String location) {
        Product old_product = productRepository.findProductById(id);

        Product new_product = productRepository.findAllProductByCodeAndLocation(old_product.getCode(), location);

        // Check whether two product are located in same place
        if (!old_product.getLocation().equalsIgnoreCase(location)){
            if (new_product == null) {
                // If there is no record in the destination, then create new record
                new_product = new Product(old_product.getName(), old_product.getCode(), weight, location);
                productRepository.save(new_product);

                // Change the source's quantity
                old_product.setWeight(old_product.getWeight() - weight);
                productRepository.save(old_product);

            } else {
                // If there is a record in the destination, then update record
                new_product.setWeight(new_product.getWeight() + weight);
                productRepository.save(new_product);

                // Change the source's quantity
                old_product.setWeight(old_product.getWeight() - weight);
                productRepository.save(old_product);
            }

            System.out.println("Product Has Changed!");
        } else {
            System.out.println("Two location is same, Request rejected!");
        }

        return "<form action=\"/\">" +
                "<h1>Have Updated the Record!</h1>" +
                "<div><button>Back To Home Page</button></div>" +
                "</form>";
    }
}
