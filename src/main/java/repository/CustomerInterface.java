package repository;

import entity.Customer;
import entity.Order;

import java.util.List;

public interface CustomerInterface extends UserInterface<Customer>{

    List<Order> findOrderByUserId(int id);
}
