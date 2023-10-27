package com.kharzixen.cartservice.repository;

import com.kharzixen.cartservice.model.Cart;
import com.kharzixen.cartservice.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

    Long deleteByCart(Cart cart);

    @Query("{'cart.$id': ?0}")
    Long deleteItemsByCartId(String cartId);
}
