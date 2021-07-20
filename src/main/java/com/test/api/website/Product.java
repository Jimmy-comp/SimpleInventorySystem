package com.test.api.website;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {
    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String name;
    private String code;
    private Integer weight;
    private String location;

    public Product(String name, String code, Integer weight, String location){
        this.name = name;
        this.code = code;
        this.weight = weight;
        this.location = location.toUpperCase().replaceAll("\\s", "");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getLocation() { return location; }

    public void setLocation(String location) {
        this.location = location;
    }
}
