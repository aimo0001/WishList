package com.example.digitalwishlist.Service;

import com.example.digitalwishlist.Model.Wish;
import com.example.digitalwishlist.Model.Wishlist;
import com.example.digitalwishlist.Repository.WishListRepository;
import com.example.digitalwishlist.Repository.WishRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class WishListService {

    private final WishListRepository wishlists;
    private final WishRepository wishes;

    public WishListService(WishListRepository wishlists, WishRepository wishes) {
        this.wishlists = wishlists;
        this.wishes = wishes;
    }

    public List<Wishlist> mine(String ownerName) {
        return wishlists.findAllByOwnerName(ownerName);
    }

    public Wishlist create(String ownerName, String title, boolean isPublic) {
        String slug = uniqueSlug(title);
        Wishlist wl = new Wishlist();
        wl.setOwnerName(ownerName);
        wl.setTitle(title);
        wl.setPublicList(isPublic);
        wl.setSlug(slug);
        return wishlists.save(wl);
    }

    public Wish addWish(Long wishlistId, String title, String url, Double price, String notes) {
        Wishlist wl = wishlists.findById(wishlistId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found: " + wishlistId));
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
        String cand = base;
        int i = 2;
        while (wishlists.existsBySlug(cand)) {
            cand = base + "-" + i++;
        }
        return cand;
    }

    private static String slugify(String s) {
        String n = Normalizer.normalize(s == null ? "" : s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
        return n.isBlank() ? "liste" : n;
    }
}
