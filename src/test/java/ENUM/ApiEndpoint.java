package ENUM;

public enum ApiEndpoint {
    PRODUCTS_LIST("/api/productsList"),
    BRANDS_LIST("/api/brandsList"),
    SEARCH_PRODUCT("/api/searchProduct"),
    VERIFY_LOGIN("/api/verifyLogin");

    private final String APIpath;

    ApiEndpoint(String path) {
        this.APIpath = path;
    }

    public String getPath() {
        return APIpath;
    }
}
