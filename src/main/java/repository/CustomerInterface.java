package repository;

import entity.Customer;
import entity.Orders;

import java.util.List;

public interface CustomerInterface extends UserInterface<Customer>{

    List<Orders> findOrderByUserId(int id);
}
