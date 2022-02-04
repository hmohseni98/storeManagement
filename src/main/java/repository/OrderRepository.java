package repository;

import entity.Order;

import java.util.List;

public class OrderRepository implements BaseRepository<Order> {
    @Override
    public int save(Order order) {
        return 0;
    }

    @Override
    public void update(Order order) {

    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Order findById(int id) {
        return null;
    }
}
