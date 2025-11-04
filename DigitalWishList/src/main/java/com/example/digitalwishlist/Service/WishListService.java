package com.example.digitalwishlist.Service;

import com.example.digitalwishlist.Model.User;
import com.example.digitalwishlist.Model.Wish;
import com.example.digitalwishlist.Model.Wishlist;
import com.example.digitalwishlist.Repository.UserRepository;
import com.example.digitalwishlist.Repository.WishListRepository;
import com.example.digitalwishlist.Repository.WishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class WishListService {
    private final WishListRepository wishlists;
    private final WishRepository wishes;
    private final UserRepository users;

    public WishListService(WishListRepository wishlists, WishRepository wishes, UserRepository users) {
        this.wishlists = wishlists; this.wishes = wishes; this.users = users;
    }

    public List<Wishlist> mine(String email) {
        User owner = users.findByEmail(email).orElseThrow();
        return wishlists.findAllByOwner(owner);
    }

    @Transactional
    public Wishlist create(String email, String title, boolean isPublic) {
        User owner = users.findByEmail(email).orElseThrow();
        String slug = uniqueSlug(title);
        Wishlist wl = new Wishlist();
        wl.setOwner(owner);
        wl.setTitle(title);
        wl.setPublicList(isPublic);
        wl.setSlug(slug);
        return wishlists.save(wl);
    }

    @Transactional
    public Wish addWish(Long wishlistId, String title, String url, Double price, String notes) {
        Wishlist wl = wishlists.findById(wishlistId).orElseThrow();
        Wish w = new Wish();
        w.setWishlist(wl);
        w.setTitle(title);
        w.setUrl(url);
        w.setPrice(price);
        w.setNotes(notes);
        return wishes.save(w);
    }

    public Optional<Wishlist> findPublicBySlug(String slug) {
        return wishlists.findBySlug(slug).filter(Wishlist::isPublicList);
    }

    private String uniqueSlug(String title) {
        String base = slugify(title);
        String candidate = base;
        int i = 2;
        while (wishlists.existsBySlug(candidate)) candidate = base + "-" + i++;
        return candidate;
    }

    private static String slugify(String s) {
        String n = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
        return n.isBlank() ? "liste" : n;
    }
}
