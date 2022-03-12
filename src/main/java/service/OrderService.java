package service;

import entity.Orders;
import repository.OrderRepository;

public class OrderService extends ShopService<Orders, OrderRepository> {

    public OrderService() {
        super(new OrderRepository());
    }
}
