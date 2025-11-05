package com.example.digitalwishlist.Repository;

import com.example.digitalwishlist.Model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findAllByWishlistId(Long id);

    List<Wish> findAllByWishlist_SlugAndWishlist_PublicListTrue(String slug);
}
