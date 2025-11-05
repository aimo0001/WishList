package com.example.digitalwishlist.Service;

import com.example.digitalwishlist.Model.Wish;
import com.example.digitalwishlist.Model.WishList;
import com.example.digitalwishlist.Repository.WishListRepository;
import com.example.digitalwishlist.Repository.WishRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;

@Service
public class WishListService {

    private final WishListRepository lists;
    private final WishRepository wishes;
    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository lists, WishRepository wishes, WishListRepository wishListRepository) {
        this.lists = lists;
        this.wishes = wishes;
        this.wishListRepository = wishListRepository;
    }

    public List<WishList> allPublic(){
        return lists.findAllByPublicListTrue();
    }

    public List<WishList> byOwner(String owner){
        if(owner == null || owner.trim().isEmpty()){
            return lists.findAll();
        }
        return lists.findAllByOwnerName(owner);
    }

    public WishList find(Long id){
        return lists.findById(id).orElse(null);
    }

    public WishList publicBySlug(String slug){
        return lists.findBySlug(slug).orElse(null);
    }

    public List<Wish> wishesByList(Long id){
        return wishes.findAllByWishlistId(id);
    }

    public List<Wish> wishesPublic(String slug){
        return wishes.findAllByWishlist_SlugAndWishlist_PublicListTrue(slug);
    }

    @Transactional
    public WishList create(String ownerName, String title){
        WishList wl = new WishList();
        wl.setOwnerName(ownerName);
        wl.setTitle(title);
        wl.setSlug(slugify(title));
        wl.setPublicList(true);

        return lists.save(wl);
    }

    @Transactional
    public Wish addWish(Long listId, String title, String url, Double price, String notes){
        WishList wl = lists.findById(listId).orElseThrow();
        Wish w = new Wish();
        w.setWishlist(wl);
        w.setTitle(title);
        w.setUrl(url);
        w.setPrice(price);
        w.setNotes(notes);
        return wishes.save(w);
    }

    private static String slugify(String s) {
        String n = Normalizer.normalize(s == null ? "" : s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
        return n.isEmpty() ? "liste" : n;
    }

    public List<WishList> findAll() {
        return wishListRepository.findAll();
    }

    public WishList findById(long id) {
        return wishListRepository.findById(id).orElse(null);
    }

    public WishList save(WishList wishlist) {
        return wishListRepository.save(wishlist);
    }

    public void delete(long id) {
        wishListRepository.deleteById(id);
    }
}

