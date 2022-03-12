package entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Customer extends User{

    private String address;


    public Customer(String username, String password, String address) {
        super(username, password);
        this.address = address;
    }

    public Customer(int id, String username, String password, String address) {
        super(id, username, password);
        this.address = address;
    }
}
