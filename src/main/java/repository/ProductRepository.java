package repository;

import entity.Product;

import java.util.List;

public class ProductRepository implements ProductInterface {


    @Override
    public int save(Product product) {
        return 0;
    }

    @Override
    public void update(Product product) {

    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Product findById(int id) {
        return null;
    }

    @Override
    public List<Product> findByCategory(int categoryId) {
        return null;
    }
}
