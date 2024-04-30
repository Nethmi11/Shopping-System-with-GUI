import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import javax.swing.SwingUtilities;

public class WestminsterShoppingManager implements ShoppingManager {

    private Scanner input = new Scanner(System.in);
    private ArrayList<Product> sysProductList;
    // product list in system variable
    private ArrayList<Product> systemProductList;
    private final int maxProducts = 50;

    public WestminsterShoppingManager() {
        this.systemProductList = new ArrayList<>();
        this.sysProductList=new ArrayList<>();
        loadDataFromFile(); // Load existing products from file on startup
    }

    // Method implemented to display a menu in the console containing the asked management actions
    public void displayMenu() {
        int option;
        System.out.println("          WELCOME TO THE SHOPPING SYSTEM            ");
        do {
            System.out.println("----------------------------------------------------");
            System.out.println("                      OPTIONS                       ");
            System.out.println("Please select an option: \n");
            System.out.println("1)Add new product to system");
            System.out.println("2)Delete product from system");
            System.out.println("3)Print product list in system");
            System.out.println("4)Save product list in system, in a file");
            System.out.println("5)Open GUI");
            System.out.println("0)Quit the program");
            System.out.println("--------------------------------------------------- ");

            System.out.print("Enter your option: ");
            option = input.nextInt();

            System.out.println();

            switch (option) {
                case 0:
                    System.out.println("Exiting the program!");
                    System.exit(0);
                    break;
                case 1:
                    System.out.println("Enter product details");
                    addProductSystem();
                    break;
                case 2:
                    deleteProductSystem();
                    break;
                case 3:
                    printProductListSys();
                    break;
                case 4:
                    saveToFile();
                    break;
                case 5:
                    sysProductList =sortMethod();
                    ShoppingPageGUI shoppingPageGUI = new ShoppingPageGUI(systemProductList);
                    shoppingPageGUI.setVisible(true);
                    break;
                default:
                    System.out.println("Your number is invalid,Please re-enter a number");
            }
        } while (true);
    }

    // add new product to system
    @Override
    public void addProductSystem() {
        if (systemProductList.size() < maxProducts) {
            while (true) {
                try {
                    System.out.print("Enter product ID: ");
                    String productId = input.next();

                    for (Product product : systemProductList) {
                        if (product.getProductID().equals(productId)) {
                            System.out.println("Product with ID " + productId + " already exists.");
                            return;
                        }
                    }

                    System.out.print("Enter product name: ");
                    String productName = input.next();
                    input.nextLine();

                    System.out.print("Enter the quantity of item: ");
                    int noOfItemsAvailable = input.nextInt();
                    input.nextLine();

                    System.out.print("Enter price: ");
                    double price = input.nextDouble();
                    input.nextLine();

                    if (price <= 0) {
                        System.out.println("Price must be greater than 0.");
                        continue;
                    }

                    System.out.println("Select product type: \"Enter '1' for Electronics.\" \" Enter '2' for Clothing\"");
                    System.out.print("option: ");

                    int productType = input.nextInt();
                    input.nextLine();

                    switch (productType) {
                        case 1:
                            System.out.print("Enter brand: ");
                            String brand = input.next();

                            System.out.print("Enter warranty period: ");
                            int warrantyPeriod = input.nextInt();
                            input.nextLine();

                            // Create Electronics object and add to the productList
                            Product eProduct = new Electronics(productId, productName, noOfItemsAvailable, price, brand, warrantyPeriod);
                            systemProductList.add(eProduct);
                            System.out.println("Electronics product added successfully." + eProduct.displayProductDetails());
                            break;

                        case 2:
                            System.out.print("Enter size: ");
                            String size = input.next();

                            System.out.print("Enter color: ");
                            String color = input.next();

                            // Create Clothing object and add to the productList
                            Product cProduct = new Clothing(productId, productName, noOfItemsAvailable, price, size, color);
                            systemProductList.add(cProduct);
                            System.out.println("Clothing product added successfully." + cProduct.displayProductDetails());
                            break;

                        default:
                            System.out.println("Invalid choice. Product not added.");
                            System.out.print("/n");
                            continue;
                    }
                    break;

                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please re-enter valid values.");
                    input.nextLine(); // Consume invalid input
                }
            }
        }
    }


    // Method to delete product by productID
    @Override
    public void deleteProductSystem() {
        if (systemProductList.size() <= 0) {
            System.out.println("No products in system to delete");
        } else {
            System.out.print("Enter the product ID to delete: ");
            String productId = input.next();

            ListIterator<Product> iterator = systemProductList.listIterator();

            while (iterator.hasNext()) {
                Product product = iterator.next();
                if (product.getProductID().equals(productId)) {
                    System.out.println(product.displayProductDetails() + " deleted");
//                    product.displayDetails();
                    iterator.remove();
                    System.out.println("Total number of products left in system: " + systemProductList.size());
                    return;
                }
            }System.out.println("Unsuccessful, product not found");
        }
    }

    // Method sort the product based on the productID
//    public void sortMethod() {
//        Collections.sort(systemProductList,new Comparator<Product>() {
//            @Override
//            public int compare(Product product1, Product product2) {
//                return product1.getProductID().compareTo(product2.getProductID());
//            }
//        });
//    }


    public ArrayList<Product> sortMethod() {
        Collections.sort(systemProductList,new Comparator<Product>() {
            @Override
            public int compare(Product product1, Product product2) {
                return product1.getProductID().compareTo(product2.getProductID());
            }
        });
        return systemProductList;
    }

    // Method to print products in system
    @Override
    public void printProductListSys() {
        sortMethod();

        System.out.println("Heres your product list");

        for (Product product : systemProductList) {
            System.out.println(product.displayProductDetails());
        }

    }

    // Save list of products in system in a file
    @Override
    public void saveToFile() {
        try {
            FileWriter fileWriter = new FileWriter("./productsList.txt");
            BufferedWriter writer = new BufferedWriter(fileWriter);

            for (Product product : systemProductList) {
                try {
                    if (product instanceof Electronics) {
                        Electronics electronics = (Electronics) product;
                        writer.write("Electronics," + electronics.getProductID() + ","
                                + electronics.getProductName() + "," + electronics.getNoOfItemsAvailable() + ","
                                + electronics.getPrice() + "," + electronics.getBrand() + ","
                                + electronics.getWarrantyPeriod());
                    } else if (product instanceof Clothing) {
                        Clothing clothing = (Clothing) product;
                        writer.write("Clothing," + clothing.getProductID() + ","
                                + clothing.getProductName() + "," + clothing.getNoOfItemsAvailable() + ","
                                + clothing.getPrice() + "," + clothing.getSize() + ","
                                + clothing.getColor());
                    }
                    writer.newLine();
                } catch (Exception e) {
                    System.out.println("Error writing product to file: " + e.getMessage());
                    return;
                }
            }
            writer.close();
            fileWriter.close();

            System.out.println("Products saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving products to file/file creation: " + e.getMessage());
        }
    }

    // load saved products from file
    public void loadDataFromFile() {
        systemProductList.clear(); // Clear existing products

        try {
            FileReader fileReader = new FileReader("./productsList.txt");
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];

                if ("Electronics".equals(type)) {
                    Electronics electronicsProduct = new Electronics(parts[1], parts[2], Integer.parseInt(parts[3]),
                            Double.parseDouble(parts[4]), parts[5], Integer.parseInt(parts[6]));
                    systemProductList.add(electronicsProduct);
                } else if ("Clothing".equals(type)) {
                    Clothing clothingProduct = new Clothing(parts[1], parts[2], Integer.parseInt(parts[3]),
                            Double.parseDouble(parts[4]), parts[5], parts[6]);
                    systemProductList.add(clothingProduct);
                }
            }
            System.out.println("Products loaded from file successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }

    }


}



//******************************************************************************************************
//reference
//        https://www.youtube.com/watch?v=Ry7ZDHbFSn0&t=432s
//        https://www.youtube.com/watch?v=TJaYTDT4t2o
//        https://www.youtube.com/watch?v=klCmA2Eeu70