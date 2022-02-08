package entity;

public class Order {

    private int id;
    private int productId;
    private int customerId;
    private ShoppingCard shoppingCard;

    public Order(int id, int productId, int customerId, ShoppingCard shoppingCard) {
        this.id = id;
        this.productId = productId;
        this.customerId = customerId;
        this.shoppingCard = shoppingCard;
    }

    public Order(int productId, int customerId, ShoppingCard shoppingCard) {
        this.productId = productId;
        this.customerId = customerId;
        this.shoppingCard = shoppingCard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public ShoppingCard getShoppingCard() {
        return shoppingCard;
    }

    public void setShoppingCard(ShoppingCard shoppingCard) {
        this.shoppingCard = shoppingCard;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productId=" + productId +
                ", customerId=" + customerId +
                ", shoppingCard=" + shoppingCard +
                '}';
    }
}
