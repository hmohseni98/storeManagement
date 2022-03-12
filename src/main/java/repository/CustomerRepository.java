package repository;

import database.SessionFactorySingleton;
import entity.Customer;
import database.MyConnection;
import entity.Orders;
import org.hibernate.SessionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements CustomerInterface {
    private SessionFactory sessionFactory = SessionFactorySingleton.getInstance();
    private Connection connection = MyConnection.connection;

    @Override
    public int save(Customer customer) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            try {
                session.save(customer);
                transaction.commit();
                return customer.getId();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public void update(Customer customer) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            try {
                session.update(customer);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customerList = new ArrayList<>();
        var session = sessionFactory.openSession();
        String hql = "FROM entity.Customer";
        var query = session.createQuery(hql, Customer.class);
        query.getResultStream().forEach(customerList::add);
        return customerList;
    }

    @Override
    public void delete(int id) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            try {
                String sql = "delete from customer where id=:id";
                var query = session.createNativeQuery(sql);
                query.setParameter("id", id);
                query.executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public Customer findById(int id) {
        try (var session = sessionFactory.openSession()) {
            return session.find(Customer.class, id);
        }
    }

    @Override
    public List<Orders> findOrderByUserId(int id) {
        List<Orders> orders = new ArrayList<>();
        var session = sessionFactory.openSession();
        String sql = "SELECT o.*,s.id as sid,s.date,s.payed FROM orders o " +
                "INNER JOIN shoppingcard s " +
                "ON o.shoppingcard_id = s.id " +
                "WHERE o.customerid = :id AND s.payed = false ";
        var query = session.createNativeQuery(sql, Orders.class);
        query.setParameter("id", id);
        query.getResultStream().forEach(orders::add);
        return orders;
    }

    @Override
    public Customer login(String username, String password) {
        var session = sessionFactory.openSession();
        String sql = "SELECT * FROM customer WHERE username = :username and password = :password";
        var query = session.createNativeQuery(sql, Customer.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return query.getSingleResult();
    }
}
