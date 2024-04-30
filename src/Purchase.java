import java.time.LocalDateTime;

public class Purchase {

    private double totalPrice;
    private LocalDateTime purchaseDate;

    public Purchase(double totalPrice) {
        this.totalPrice = totalPrice;
        this.purchaseDate = LocalDateTime.now();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }
}