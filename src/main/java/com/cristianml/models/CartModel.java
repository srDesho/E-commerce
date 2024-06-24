package com.cristianml.models;

import com.cristianml.security.model.UserModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class CartModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserModel user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemModel> cartItems = new ArrayList<>();

    // Method to add a CartItem to the cart and set the relationship
    public void addCartItem(CartItemModel cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);
    }
}
