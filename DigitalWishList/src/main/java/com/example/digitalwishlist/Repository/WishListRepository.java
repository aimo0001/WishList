package com.example.digitalwishlist.Repository;

import com.example.digitalwishlist.Model.WishList;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    Optional<WishList> findBySlug(String slug);

    @EntityGraph(attributePaths = "wishes")
    List<WishList> findAllByOwnerName(String ownerName);

    List<WishList> findAllByPublicListTrue();
}
