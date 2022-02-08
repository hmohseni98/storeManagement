import customException.InvalidAccount;
import customException.InvalidNationalCode;
import customException.RecordDoesNotExist;
import database.FillDatabase;
import entity.*;
import service.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static int numberOfOption;
    private static int typeOfUser;
    private static final AdminService adminService = new AdminService();
    private static final CustomerService customerService = new CustomerService();
    private static final CategoryService categoryService = new CategoryService();
    private static final ProductService productService = new ProductService();
    private static final ShoppingCardService shoppingCardService = new ShoppingCardService();
    private static final OrderService orderService = new OrderService();
    private static Admin admin;
    private static Customer customer;

    public static void main(String[] args) {
        //1. run database > CreateTable.sql .
        //2. run program with fillDatabase.fillData() then commend it.
        FillDatabase fillDatabase = new FillDatabase();
        fillDatabase.fillData();
        userMenu();
    }

    public static void userMenu() {
        try {
            System.out.println("1.admin");
            System.out.println("2.customer");
            System.out.print("please select one option:");
            typeOfUser = scanner.nextInt();
            if (typeOfUser == 1 || typeOfUser == 2)
                welcomeMenu();
            else {
                System.out.println("invalid option!");
                userMenu();
            }

        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("invalid character");
            userMenu();
        }
    }

    public static void welcomeMenu() {
        try {
            System.out.println("1.sign in");
            System.out.println("2.sign up");
            System.out.print("please select one option:");
            numberOfOption = scanner.nextInt();
            if (numberOfOption == 1) {
                loginMenu();
            } else if (numberOfOption == 2 && typeOfUser == 1) {
                registerAdmin();
            } else if (numberOfOption == 2 && typeOfUser == 2) {
                registerCustomer();
            } else {
                System.out.println("invalid option");
                welcomeMenu();
            }
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("invalid character");
            welcomeMenu();
        }
    }

    public static void loginMenu() {
        System.out.print("please enter username:");
        String username = scanner.next();
        System.out.print("please enter password:");
        String password = scanner.next();
        checkAccount(username, password);

    }

    public static void checkAccount(String username, String password) {
        try {
            if (typeOfUser == 1) {
                admin = adminService.login(username, password);
                adminMenu();
            } else if (typeOfUser == 2) {
                customer = customerService.login(username, password);
                customerMenu();
            }
        } catch (InvalidAccount e) {
            System.out.println(e.toString());
            userMenu();
        }
    }

    public static void registerAdmin() {
        try {
            System.out.print("please enter username:");
            String username = scanner.next();
            System.out.print("please enter password:");
            String password = scanner.next();
            System.out.print("please enter national code:");
            scanner.nextLine();
            String nationalCode = scanner.next();
            nationalCodeValid(nationalCode);
            adminService.save(new Admin(username, password, nationalCode));
            System.out.println("register success!");
            userMenu();
        } catch (InvalidNationalCode e) {
            System.out.println(e.toString());
            registerAdmin();
        }
    }

    public static void nationalCodeValid(String nc) {
        char[] chars = nc.toCharArray();
        for (char c : chars) {
            if (!Character.isDigit(c))
                throw new InvalidNationalCode();
        }
        if (nc.length() != 10)
            throw new InvalidNationalCode();
    }

    public static void registerCustomer() {
        System.out.print("please enter username:");
        String username = scanner.next();
        System.out.print("please enter password:");
        String password = scanner.next();
        System.out.print("please enter address:");
        scanner.nextLine();
        String address = scanner.nextLine();
        customerService.save(new Customer(username, password, address));
        System.out.println("register success!");
        userMenu();
    }

    public static void adminMenu() {
        try {
            System.out.println("1.add new category");
            System.out.println("2.add new product");
            System.out.println("3.update existing products");
            System.out.println("4.show category");
            System.out.println("5.show products");
            System.out.println("6.remove category");
            System.out.println("7.remove product");
            System.out.println("8.back to login menu");
            System.out.print("please select one option:");
            numberOfOption = scanner.nextInt();
            if (numberOfOption == 1) {
                addCategoryMenu();
            } else if (numberOfOption == 2) {
                addProductMenu();
            } else if (numberOfOption == 3) {
                updateProduct();
            } else if (numberOfOption == 4) {
                showCategory();
            } else if (numberOfOption == 5) {
                categoryListMenu();
            } else if (numberOfOption == 6) {
                removeCategory();
            } else if (numberOfOption == 7) {
                removeProduct();
            } else if (numberOfOption == 8) {
                userMenu();
            } else {
                System.out.println("invalid number");
                adminMenu();
            }
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("invalid character!");
            adminMenu();
        }

    }

    public static void addCategoryMenu() {
        try {
            System.out.print("please enter title:");
            scanner.nextLine();
            String title = scanner.nextLine();
            System.out.print("please enter description:");
            String description = scanner.nextLine();
            System.out.print("please enter parent category id:");
            int parentCategory = scanner.nextInt();
            categoryService.save(new Category(title, description, parentCategory));
            System.out.println("added success!");
            adminMenu();
        } catch (NullPointerException e) {
            scanner.nextLine();
            System.out.println("parent category does not exist!");
            adminMenu();
        }
    }

    public static void addProductMenu() {
        try {
            System.out.print("please enter name:");
            scanner.nextLine();
            String name = scanner.nextLine();
            System.out.print("please enter description:");
            String description = scanner.nextLine();
            System.out.print("please enter category id:");
            int CategoryId = scanner.nextInt();
            System.out.print("please enter qty:");
            int qty = scanner.nextInt();
            System.out.print("please enter price:");
            int price = scanner.nextInt();
            productService.save(new Product(name, description, categoryService.findById(CategoryId), qty, price));
            System.out.println("added success!");
            adminMenu();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("invalid character");
            adminMenu();
        } catch (RecordDoesNotExist e) {
            System.out.println(e.toString());
            adminMenu();
        }
    }

    public static void updateProduct() {
        try {
            System.out.print("please enter product id:");
            int id = scanner.nextInt();
            System.out.print("please enter new name:");
            scanner.nextLine();
            String name = scanner.nextLine();
            System.out.print("please enter new description:");
            String description = scanner.nextLine();
            System.out.print("please enter new category id:");
            int CategoryId = scanner.nextInt();
            System.out.print("please enter new qty:");
            int qty = scanner.nextInt();
            System.out.print("please enter new price:");
            int price = scanner.nextInt();
            Product product = productService.findById(id);
            productService.update(new Product(product.getId(), name, description, categoryService.findById(CategoryId), qty, price));
            System.out.println("update success!");
            adminMenu();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("invalid character");
            adminMenu();
        } catch (RecordDoesNotExist e) {
            System.out.println(e.toString());
            adminMenu();
        }
    }

    public static void showCategory() {
        List<Category> categoryList = categoryService.findAll();
        for (Category c : categoryList) {
            System.out.println(c.toString());
        }
        adminMenu();
    }

    public static void removeCategory() {
        try {
            System.out.print("please enter category id:");
            int id = scanner.nextInt();
            categoryService.delete(id);
            System.out.println("remove success!");
            adminMenu();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("invalid character");
            adminMenu();
        }
    }

    public static void removeProduct() {
        try {
            System.out.print("please enter product id:");
            int id = scanner.nextInt();
            productService.delete(id);
            System.out.println("remove success!");
            adminMenu();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("invalid character");
            adminMenu();
        }
    }

    public static void customerMenu() {
        try {
            System.out.println("1.show product");
            System.out.println("2.add to order list");
            System.out.println("3.show order list");
            System.out.println("4.remove from order list");
            System.out.println("5.Complete the purchase");
            System.out.println("6.back to login menu");
            System.out.print("please select one option:");
            numberOfOption = scanner.nextInt();
            if (numberOfOption == 1) {
                categoryListMenu();
            } else if (numberOfOption == 2) {
                addToShoppingCardMenu();
            } else if (numberOfOption == 3) {
                showShoppingCard();
            } else if (numberOfOption == 4) {
                removeFromShoppingCard();
            } else if (numberOfOption == 5) {
                completeThePurchase();
            } else if (numberOfOption == 6) {
                userMenu();
            } else {
                System.out.println("invalid number");
                customerMenu();
            }
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("invalid character");
            customerMenu();
        }
    }

    public static void categoryListMenu() {
        try {
            System.out.println("1.smartphone");
            System.out.println("2.tablet");
            System.out.println("3.laptop");
            System.out.println("4.accessory");
            System.out.print("please select one option:");
            numberOfOption = scanner.nextInt();
            if (numberOfOption == 1) {
                productService.findByCategory(numberOfOption);
                if (typeOfUser == 1)
                    adminMenu();
                else
                    customerMenu();
            } else if (numberOfOption == 2) {
                productService.findByCategory(numberOfOption);
                if (typeOfUser == 1)
                    adminMenu();
                else
                    customerMenu();
            } else if (numberOfOption == 3) {
                productService.findByCategory(numberOfOption);
                if (typeOfUser == 1)
                    adminMenu();
                else
                    customerMenu();
            } else if (numberOfOption == 4) {
                productService.findByCategory(numberOfOption);
                if (typeOfUser == 1)
                    adminMenu();
                else
                    customerMenu();
            } else {
                System.out.println("invalid number");
                if (typeOfUser == 1)
                    adminMenu();
                else
                    customerMenu();
            }
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("invalid character");
            if (typeOfUser == 1)
                adminMenu();
            else
                customerMenu();
        }
    }

    public static void addToShoppingCardMenu() {
        try {
            System.out.print("please enter product id:");
            int id = scanner.nextInt();
            ShoppingCard shoppingCard = new ShoppingCard(Date.valueOf(LocalDate.now()));
            shoppingCard.setId(shoppingCardService.save(shoppingCard));
            orderService.save(new Order(id, customer.getId(), shoppingCard));
            System.out.println("added success!");
            customerMenu();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("invalid character");
            customerMenu();
        } catch (NullPointerException e) {
            System.out.println("record does not exist!");
            customerMenu();
        }
    }

    public static void showShoppingCard() {
        try {
            customerService.findOrderByUserId(customer.getId());
            customerMenu();
        } catch (RecordDoesNotExist e) {
            System.out.println(e.toString());
            customerMenu();
        }
    }

    public static void removeFromShoppingCard() {
        try {
            System.out.print("please enter order id:");
            int id = scanner.nextInt();
            orderService.delete(id);
            System.out.println("remove success!");
            customerMenu();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("invalid character");
            customerMenu();
        } catch (RecordDoesNotExist e) {
            System.out.println(e.toString());
            customerMenu();
        }
    }

    public static void completeThePurchase() {
        try {
            System.out.print("please enter shopping card id:");
            int id = scanner.nextInt();
            shoppingCardService.update(new ShoppingCard(id, Date.valueOf(LocalDate.now()), true));
            productService.updateQty(productService.findById(productService.findProductIdByShoppingCardId(id)));
            System.out.println("complete The Purchase!");
            customerMenu();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("invalid character");
            customerMenu();
        } catch (RecordDoesNotExist e) {
            System.out.println(e.toString());
            customerMenu();
        }
    }
}