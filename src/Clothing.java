// Clothing subclass
public class Clothing extends Product {

    private String size;
    private String color;

    public Clothing(String productId, String productName, int availableItems, double price, String size, String color) {
        super(productId, productName, availableItems, price);
        this.size = size;
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String displayDetails() {
        return ("Product ID: " + getProductID() + "\n" +
                "Category: " + getProductType() + "\n" +
                "Product Name: " + getProductName() + "\n" +
                "Size: " + getSize() + "\n" +
                "Color: " + getColor() + "\n" +
                "Available Items: " + getNoOfItemsAvailable());
    }

    //short display method
    @Override
    public String displayProductDetails() {
        return("Product type: " + getProductType()+" Product ID: " + getProductID() + ", Name: " + getProductName() +
                ", Quantity: " + getNoOfItemsAvailable() + ", Price: " + getPrice() +
                ", Size: " + size + ", Color: " + color);
    }

    @Override
    public String getProductType(){
        return "Clothing";
    }
    @Override
    public String getAdditionalInfo() {
        return getSize() + ", " + getColor();
    }
}
