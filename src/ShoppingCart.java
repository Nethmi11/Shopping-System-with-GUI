import java.util.ArrayList;


public class ShoppingCart {
    private ArrayList<Product> cartProductList;
    private double cartTotal;

    private ArrayList<Purchase> historyOfPurchase=new ArrayList<>();

    public ShoppingCart(){
        this.cartProductList =new ArrayList<>();
        this.cartTotal = 0;
    }

    public void addProduct(Product product){
        cartProductList.add(product);
    }

    public void removeProduct(Product product){
        cartProductList.remove(product);
    }

    public ArrayList<Product> getCartProductList() {
        return cartProductList;
    }

    public int getProductQuantity(Product product) {
        int count = 0;
        for (Product p : cartProductList) {
            if (p.getProductID().equals(product.getProductID())) {
                count++;
            }
        }
        return count;
    }
    public double totalCost(){
        cartTotal=0;
        for(Product product: cartProductList){
            cartTotal+=product.getPrice();
        }
        return cartTotal;
    }
    public boolean hasThreeItemsInSameCategory() {
        int electronicsCount = 0;
        int clothingCount = 0;

        for (Product item : cartProductList) {
            String productType = item.getProductType();

            if ("Electronics".equals(productType)) {
                electronicsCount++;
            } else if ("Clothing".equals(productType)) {
                clothingCount++;
            }
            if (electronicsCount >= 3 || clothingCount >= 3) {
                return true;
            }
        }
        return false;
    }


    public boolean isFirstPurchase() {

        if ( historyOfPurchase.isEmpty()) {
            return true;
        }else{
            return false;
        }
    }

    public void checkout(){
        // Add the current purchase to the purchase  history
        Purchase newPurchase = new Purchase(cartTotal);
        historyOfPurchase.add(newPurchase);

        cartProductList.clear();
        cartTotal = 0;
    }

}