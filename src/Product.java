
public abstract class Product {

    private String productId;
    private String productName;
    private int noOfItemsAvailable;
    private double price;

    public Product(String productId, String productName, int noOfItemsAvailable, double price) {
        this.productId = productId;
        this.productName = productName;
        this.noOfItemsAvailable = noOfItemsAvailable;
        this.price = price;
    }

    public String getProductID(){
        return productId;
    }
    public void setProductID(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNoOfItemsAvailable() {
        return noOfItemsAvailable;
    }

    public void setNoOfItemsAvailable(int noOfItemsAvailable) {
        this.noOfItemsAvailable = noOfItemsAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Abstract method
    public abstract String displayDetails();

    public abstract String displayProductDetails();

    // Abstract method
    public abstract String getProductType();

    // Abstract method
    public abstract String getAdditionalInfo();
}