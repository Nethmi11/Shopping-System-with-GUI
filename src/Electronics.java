public class Electronics extends Product {

    private String brand;
    private int warrantyPeriod;

    public Electronics(String productID, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
        super(productID, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String displayDetails() {
        return ("Product ID: " + getProductID() + "\n" +
                "Product Name: " + getProductName() + "\n" +
                "Price: $" + getPrice() + "\n" +
                "Brand: " + getBrand() + "\n" +
                "Warranty Period: " + getWarrantyPeriod() + "\n" +
                "Available Items: " + getNoOfItemsAvailable());
    }

    //short diaplay method
    @Override
    public String displayProductDetails() {
        return( "Product type: " + getProductType()+", Product ID: " + getProductID() + ", Name: " + getProductName() +
                ", Quantity: " + getNoOfItemsAvailable() + ", Price: " + getPrice() +
                ", Brand: " + brand + ", Warranty Period: " + warrantyPeriod + " months"
        );
    }

    @Override
    public String getProductType() {
        return "Electronics";
    }

    @Override
    public String getAdditionalInfo() {
        return getBrand() + ", " + getWarrantyPeriod() + " months warranty";
    }
}
