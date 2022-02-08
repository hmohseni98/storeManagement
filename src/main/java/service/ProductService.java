package service;

import entity.Product;
import repository.ProductRepository;

import java.util.List;

public class ProductService extends ShopService<Product, ProductRepository> {

    private ProductRepository productRepository;

    public ProductService() {
        super(new ProductRepository());
        productRepository=new ProductRepository();
    }

    public void findByCategory(int id) {
            List<Product> byCategory = productRepository.findByCategory(id);
            for (Product product : byCategory) {
                System.out.println(product.toString());
            }
    }
    public void updateQty(Product product){
        productRepository.updateQty(product);
    }
    public Integer findProductIdByShoppingCardId (int shoppingCardId){
        return productRepository.findProductIdByShoppingCardId(shoppingCardId);
    }
}
