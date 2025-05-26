package api.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignore unexpected fields at the top level
public class ProductResponse {

    public List<Product> products;

    @JsonIgnoreProperties(ignoreUnknown = true) // Ignore unknown fields like "otherDetails" in a product
    public static class Product {
        public String id;
        public String name;
        public String price;
        public String brand;
        public Category category;

        @Override
        public String toString() {
            return String.format("%s (id=%s, price=%s)", name, id, price);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true) // Ignore unknown fields like "usertype" in category
    public static class Category {
        public String id;
        public String category;
        public String subcategory;

        @Override
        public String toString() {
            return String.format("%s → %s", category, subcategory);
        }
    }
}