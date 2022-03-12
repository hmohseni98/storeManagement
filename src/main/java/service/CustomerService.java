package service;

import entity.Customer;
import entity.Orders;
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
        List<Orders> OrdersByUserId = customerRepository.findOrderByUserId(id);
        for (Orders Orders : OrdersByUserId) {
            System.out.println("Orders{" +
                    "id=" + Orders.getId() +
                    ", productId=" + Orders.getProductId() +
                    ", productName=" + productService.findById(Orders.getProductId()).getName() +
                    ", " + Orders.getShoppingCard() +
                    '}');
        }
    }
}
