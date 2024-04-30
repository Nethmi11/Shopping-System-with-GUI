import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartGUI extends JFrame {
    private JTable cartTable;
    private DefaultTableModel cartTableModel;
    private ShoppingCart shoppingCart;
    private List<String> productIdsInTable = new ArrayList<>();

    private JLabel totalLabel,totCalLabel, firstPurchaseLabel,fpCalLabel, categoryLabel,categoryCalLabel,
            finalTotalLabel,finalTotCalLabel;

    public ShoppingCartGUI(ShoppingCart shoppingCart) {
        setTitle("Shopping Cart");
        setSize(700, 500);

        this.shoppingCart = shoppingCart;

        //creating tabel
        String[] columnNames = {"Product", "Quantity", "TPrice ($)"};
        cartTableModel = new DefaultTableModel(columnNames, 0);
        cartTable = new JTable(cartTableModel);
        cartTable.setRowHeight(30);

        JTableHeader tableHeader = cartTable.getTableHeader();
        tableHeader.setFont(tableHeader.getFont().deriveFont(Font.BOLD));

        // Adding the cart table to a scroll pane
        JScrollPane tableScrollPane = new JScrollPane(cartTable);

        // Panel for displaying discount information
        JPanel discountPanel = new JPanel(new GridLayout(4, 2));
        discountPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // JLabels for discount information
        totalLabel = new JLabel("Total");
        totCalLabel = new JLabel("");
        firstPurchaseLabel = new JLabel("First Purchase Discount (10%)");
        fpCalLabel = new JLabel("");
        categoryLabel = new JLabel("Three Items in Same Category Discount (20%)");
        categoryCalLabel = new JLabel("");
        finalTotalLabel = new JLabel("Final Total");
        finalTotCalLabel = new JLabel("");

        discountPanel.add(totalLabel);
        discountPanel.add(totCalLabel);
        discountPanel.add(firstPurchaseLabel);
        discountPanel.add(fpCalLabel);
        discountPanel.add(categoryLabel);
        discountPanel.add(categoryCalLabel);
        discountPanel.add(finalTotalLabel);
        discountPanel.add(finalTotCalLabel);

        // JPanel for both table and discount panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // table and discountPanel added to MainPanel
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(discountPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //TODO check this

    }

    // Method to update the cart table when product is added to cart
    public void updateCartTable(Product selectedProduct) {
        DefaultTableModel model = (DefaultTableModel) cartTable.getModel();
        // Created a global variable arraylist productIdsInTable list to keep track of product rows in the table

        String productId = selectedProduct.getProductID();
        String productInfo = productId+","+selectedProduct.getProductName()+", "+selectedProduct.getAdditionalInfo();
        int quantity = shoppingCart.getProductQuantity(selectedProduct);
        double totalPrice = selectedProduct.getPrice() * quantity;

        // Checks if the product ID is in the list of product IDs already in the table
        if (productIdsInTable.contains(productId)) {
            int rowIndex = productIdsInTable.indexOf(productId);
            model.setValueAt(quantity, rowIndex, 1);
            model.setValueAt(totalPrice, rowIndex, 2);
        } else {
            productIdsInTable.add(productId);
            Object[] rowData = new Object[]{productInfo, quantity, totalPrice};
            model.addRow(rowData);

        }
        updateDiscountLabels();

    }

    // Method to update the discount-related labels
    public void updateDiscountLabels() {

        double totalCost = shoppingCart.totalCost();

        double firstPurchaseDiscount = 0;
        if (shoppingCart.isFirstPurchase()) {
            firstPurchaseDiscount = 0.1 * totalCost;
        }

        double categoryDiscount = 0;
        if (shoppingCart.hasThreeItemsInSameCategory()) {
            categoryDiscount = 0.2 * totalCost;
        }

        double finalTotal = totalCost - firstPurchaseDiscount - categoryDiscount;

        // Setting text for the empty labels with "$" sign
        totCalLabel.setText("$ " + totalCost);
        fpCalLabel.setText("$ " + firstPurchaseDiscount);
        categoryCalLabel.setText("$ " + categoryDiscount);
        finalTotCalLabel.setText("$ " + finalTotal);
    }


}
