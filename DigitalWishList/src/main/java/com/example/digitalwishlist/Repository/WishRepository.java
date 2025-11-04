package com.example.digitalwishlist.Repository;

import com.example.digitalwishlist.Model.Wish;
import com.example.digitalwishlist.Model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findAllByWishlist(Wishlist wishlist);
}
