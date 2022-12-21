package sk.stuba.fei.uim.oop.assignment3.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.productincart.ProductInCart;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<ProductInCart> shoppingList = new ArrayList<>();

    private boolean payed;
}
