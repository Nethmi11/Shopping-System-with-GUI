import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class ShoppingPageGUI extends JFrame {
    private ArrayList<Product> sysProductList;
    private JTable productTable;
    private JLabel filterLabel;
    private JButton addToCartButton,shoppingCartButton;
    private JComboBox<String> filterComboBox;
    private JTextArea productDetailsTextArea;
    private ShoppingCartGUI shoppingCartGUI;
    private ShoppingCart shoppingCart;

    public ShoppingPageGUI(ArrayList<Product> systemProductList) {
        this.sysProductList = systemProductList;
        shoppingCart = new ShoppingCart();
        this.shoppingCartGUI = new ShoppingCartGUI(shoppingCart);

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Westminster Shopping Center");
        setLayout(new BorderLayout());

        // panel for shopping cart button
        JPanel shoppingCartPanel = new JPanel();
        shoppingCartButton = new JButton("Shopping Cart");
        shoppingCartPanel.add(shoppingCartButton);
        shoppingCartPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 10));

        // panel for filtering products + sortButton
        JPanel filterPanel = new JPanel();
        filterLabel = new JLabel("Select Product Category: ");
        filterPanel.add(filterLabel);
        filterComboBox = new JComboBox(new String[]{"All", "Electronics", "Clothing"});
        filterPanel.add(filterComboBox);

        // Creating a top panel and adding shoppingCartPanel and filterPanel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(shoppingCartPanel, BorderLayout.EAST);
        topPanel.add(filterPanel, BorderLayout.SOUTH);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

        // Creating product table
        String[] columnNames = {"Product ID", "Product Name", "Category", "Price", "Info"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(model);

        for (Product product : sysProductList) {
            Object[] row = new Object[5];

            row[0] = product.getProductID();
            row[1] = product.getProductName();
            row[2] = product.getProductType();
            row[3] = product.getPrice();
            row[4] = product.getAdditionalInfo();

            model.addRow(row);
        }

        // Make the table header text bold
        productTable.getTableHeader().setFont(productTable.getTableHeader().getFont().deriveFont(Font.BOLD));
        productTable.setRowHeight(25);

        // scrollPane for product table
        JScrollPane scrollPane = new JScrollPane(productTable);
        Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
        scrollPane.setBorder(bottomBorder);

        // panel for product details textarea
        productDetailsTextArea = new JTextArea(10, 20);
        productDetailsTextArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // panel for addToCart button
        JPanel addToCartPanel = new JPanel();
        addToCartButton = new JButton("Add to cart");
        addToCartPanel.add(addToCartButton);
        addToCartPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create a bottom panel and add infoPanel and addToCartPanel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(productDetailsTextArea, BorderLayout.NORTH);
        bottomPanel.add(addToCartPanel, BorderLayout.SOUTH);

        // Create a main panel and add topPanel, scrollPane, and bottomPanel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel);


        //  filter product by category
        filterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterProducts();
            }
        });


        //selecting a row in the table
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                handleProductSelection();
            }
        });

        //clicking shopping cart button
        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openShoppingCart();
            }
        });

        //clicking add to cart button for selected product/row
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProductToCart();
            }
        });


    }

    // Filter products based on the selected option in the combo box
    public void filterProducts() {
        String selectedOption = (String) filterComboBox.getSelectedItem();
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0);

        for (Product product : sysProductList) {
            if ("Electronics".equals(selectedOption) && (product instanceof Electronics)) {
                model.addRow(new Object[]{product.getProductID(), product.getProductName(), "Electronics", product.getPrice(), product.getAdditionalInfo()});
            } else if ("Clothing".equals(selectedOption) && (product instanceof Clothing)) {
                model.addRow(new Object[]{product.getProductID(), product.getProductName(), "Clothing", product.getPrice(), product.getAdditionalInfo()});
            } else if ("All".equals(selectedOption)) {
                model.addRow(new Object[]{product.getProductID(), product.getProductName(), product.getProductType(), product.getPrice(), product.getAdditionalInfo()});
            }
        }
    }

    // Handle selection of a product in the table
    public void handleProductSelection() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            String productId = (String) productTable.getValueAt(selectedRow, 0);

            for (Product product : sysProductList) {
                if (productId.equals(product.getProductID())) {
                    String details = "Selected product details\n" + product.displayDetails();
                    productDetailsTextArea.setFont(productDetailsTextArea.getFont().deriveFont(Font.BOLD));
                    productDetailsTextArea.setText(details);
                }
            }
        } else {
            productDetailsTextArea.setText("");
        }
    }

    // opening of shoppingCartGUI when shoppingCartButton clicked
    public void openShoppingCart() {
        shoppingCartGUI.setVisible(true);
    }

    // Add a selected product to the shopping cart
    public void addProductToCart() {
        int selectedRow = productTable.getSelectedRow();
        System.out.println(selectedRow);
        if (selectedRow != -1) {
            String productId = (String) productTable.getValueAt(selectedRow, 0);

            for (Product product : sysProductList) {
                if (product.getProductID().equals(productId)) {
                    Product selectedProduct = product;
                    int noOfItemsAvailable = selectedProduct.getNoOfItemsAvailable();

                    if (noOfItemsAvailable > 0) {
                        shoppingCart.addProduct(selectedProduct);
                        selectedProduct.setNoOfItemsAvailable(noOfItemsAvailable - 1);
                        handleProductSelection();
                        shoppingCartGUI.updateCartTable(selectedProduct);
                    } else {
                        JOptionPane.showMessageDialog(this, "Product out of stock.");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product.");
        }
    }
}

//**********************************************************
// reference
// JOptionPane message - https://www.javatpoint.com/java-joptionpane

