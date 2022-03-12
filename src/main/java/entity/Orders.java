package entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int productId;
    private int customerId;

    @OneToOne
    private ShoppingCard shoppingCard;


    public Orders(int productId, int customerId, ShoppingCard shoppingCard) {
        this.productId = productId;
        this.customerId = customerId;
        this.shoppingCard = shoppingCard;
    }
}
