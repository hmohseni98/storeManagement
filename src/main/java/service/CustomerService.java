package service;

import entity.Customer;
import entity.Order;
import entity.ShoppingCard;
import repository.CustomerRepository;

import java.util.List;

public class CustomerService extends UserService<Customer, CustomerRepository> {

    private CustomerRepository customerRepository;
    private ProductService productService = new ProductService();

    public CustomerService() {
        super(new CustomerRepository());
        this.customerRepository = new CustomerRepository();
    }

    public void findOrderByUserId(int id) {
        List<Order> OrderByUserId = customerRepository.findOrderByUserId(id);
        for (Order order : OrderByUserId) {
            System.out.println("Order{" +
                    "id=" + order.getId() +
                    ", productId=" + order.getProductId() +
                    ", productName=" + productService.findById(order.getProductId()).getName() +
                    ", " + order.getShoppingCard() +
                    '}');
        }
    }
}
