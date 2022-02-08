package database;

import entity.*;
import service.*;

import java.sql.Date;

public class FillDatabase {
    private AdminService adminService = new AdminService();
    private CustomerService customerService = new CustomerService();
    private CategoryService categoryService = new CategoryService();
    private ProductService productService = new ProductService();
    private ShoppingCardService shoppingCardService = new ShoppingCardService();
    private OrderService orderService = new OrderService();

    public void fillData(){
        Admin admin = new Admin("admin","admin","0021239266");
        admin.setId(adminService.save(admin));

        Customer customer1 = new Customer("customer1","customer1","tehran - ferdos1 - p 19");
        Customer customer2 = new Customer("customer2","customer2","alborz - ferdos2 - p 14");
        Customer customer3 = new Customer("customer3","customer3","mashhad - ferdos3 - p 5");
        customer1.setId(customerService.save(customer1));
        customer2.setId(customerService.save(customer2));
        customer3.setId(customerService.save(customer3));

        Category category1 = new Category("smart phone","cell phone",1);
        Category category2 = new Category("tablet","tablet",2);
        Category category3 = new Category("laptop","laptop",3);
        Category category4 = new Category("accessory","accessory",4);
        Category category5 = new Category("huawei","huawei",1);
        Category category6 = new Category("samsung","samsung",1);
        Category category7 = new Category("sony","sony",1);
        Category category8 = new Category("xiaomi","xiaomi",1);
        Category category9 = new Category("iphone","iphone",2);
        Category category10 = new Category("asus","asus",2);
        Category category11 = new Category("samsung","samsung",2);
        Category category12 = new Category("acer","acer",3);
        Category category13 = new Category("asus","asus",3);
        Category category14 = new Category("hp","hp",3);
        Category category15 = new Category("keyboard","keyboard",4);
        Category category16 = new Category("mouse","mouse",4);
        Category category17 = new Category("headset","headset",4);

        category1.setId(categoryService.save(category1));
        category2.setId(categoryService.save(category2));
        category3.setId(categoryService.save(category3));
        category4.setId(categoryService.save(category4));
        category5.setId(categoryService.save(category5));
        category6.setId(categoryService.save(category6));
        category7.setId(categoryService.save(category7));
        category8.setId(categoryService.save(category8));
        category9.setId(categoryService.save(category9));
        category10.setId(categoryService.save(category10));
        category11.setId(categoryService.save(category11));
        category12.setId(categoryService.save(category12));
        category13.setId(categoryService.save(category13));
        category14.setId(categoryService.save(category14));
        category15.setId(categoryService.save(category15));
        category16.setId(categoryService.save(category16));
        category17.setId(categoryService.save(category17));

        Product product1 = new Product("iphone 13","iphone 13",category9,300,30000000);
        Product product2 = new Product("iphone 13 pro max","iphone 13 pro max",category9,200,49000000);
        Product product3 = new Product("iphone 13","iphone 13",category9,300,30000000);
        Product product4 = new Product("note 7","note 7",category8,150,8000000);
        Product product5 = new Product("note 8","note 8",category8,150,6000000);
        Product product6 = new Product("poco x3","poco x3",category8,200,10000000);
        Product product7 = new Product("logitech","logitech",category15,1000,1000000);
        Product product8 = new Product("a4tech","a4tech",category15,900,850000);
        Product product9 = new Product("asus laptop1","asus laptop1",category13,200,400000000);
        Product product10 = new Product("asus laptop2","asus laptop2",category13,200,350000000);
        product1.setId(productService.save(product1));
        product2.setId(productService.save(product2));
        product3.setId(productService.save(product3));
        product4.setId(productService.save(product4));
        product5.setId(productService.save(product5));
        product6.setId(productService.save(product6));
        product7.setId(productService.save(product7));
        product8.setId(productService.save(product8));
        product9.setId(productService.save(product9));
        product10.setId(productService.save(product10));

        ShoppingCard shoppingCard1 = new ShoppingCard(Date.valueOf("2022-02-01"));
        ShoppingCard shoppingCard2 = new ShoppingCard(Date.valueOf("2022-02-11"));
        ShoppingCard shoppingCard3 = new ShoppingCard(Date.valueOf("2022-02-03"));
        ShoppingCard shoppingCard4 = new ShoppingCard(Date.valueOf("2022-02-05"));
        ShoppingCard shoppingCard5 = new ShoppingCard(Date.valueOf("2022-02-13"));

        shoppingCard1.setId(shoppingCardService.save(shoppingCard1));
        shoppingCard2.setId(shoppingCardService.save(shoppingCard2));
        shoppingCard3.setId(shoppingCardService.save(shoppingCard3));
        shoppingCard4.setId(shoppingCardService.save(shoppingCard4));
        shoppingCard5.setId(shoppingCardService.save(shoppingCard5));


        Order order1 = new Order(1,1,shoppingCard1);
        Order order2 = new Order(2,1,shoppingCard2);
        Order order3 = new Order(3,2,shoppingCard3);
        Order order4 = new Order(4,2,shoppingCard4);
        Order order5 = new Order(5,2,shoppingCard5);

        order1.setId(orderService.save(order1));
        order2.setId(orderService.save(order2));
        order3.setId(orderService.save(order3));
        order4.setId(orderService.save(order4));
        order5.setId(orderService.save(order5));
    }
}
