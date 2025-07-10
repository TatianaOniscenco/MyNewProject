package api.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignore unexpected fields at the top level

public class ProductListResponse {

    public List<Product> products;

    @JsonIgnoreProperties(ignoreUnknown = true) // Ignore unknown fields like "otherDetails" in a product
    public static class Product {
        public String id;
        public String name;
        public String price;

        @Override
        public String toString() {
            return String.format("%s (id=%s, price=%s)", name, id, price);
        }
    }
}