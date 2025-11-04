package com.example.digitalwishlist.Repository;

import com.example.digitalwishlist.Model.User;
import com.example.digitalwishlist.Model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findBySlug(String slug);
    boolean existsBySlug(String slug);
    List<Wishlist> findAllByOwner(User owner);
}
