package com.example.digitalwishlist.Controller;

import com.example.digitalwishlist.Model.Wishlist;
import com.example.digitalwishlist.Service.WishListService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class WishListController {
    private final WishListService wl;

    public WishListController(WishListService wl) { this.wl = wl; }

    @GetMapping({"/", "/wishlist"})
    public String myLists(Model model, Principal me) {
        if (me == null) return "redirect:/login";
        model.addAttribute("lists", wl.mine(me.getName()));
        return "wishlist";
    }

    @PostMapping("/wishlist/create")
    public String createList(@RequestParam @NotBlank String title,
                             @RequestParam(defaultValue="false") boolean isPublic,
                             Principal me) {
        wl.create(me.getName(), title, isPublic);
        return "redirect:/wishlist";
    }

    @GetMapping("/wishlist/{id}")
    public String viewList(@PathVariable Long id, Model model, Principal me) {
        var list = wl.mine(me.getName()).stream()
                .filter(x -> x.getId().equals(id)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
        model.addAttribute("list", list);
        model.addAttribute("wishes", list.getWishes());
        return "addWish";
    }

    @PostMapping("/wishlist/{id}/wish")
    public String addWish(@PathVariable Long id,
                          @RequestParam @NotBlank String title,
                          @RequestParam(required=false) String url,
                          @RequestParam(required=false) Double price,
                          @RequestParam(required=false) String notes) {
        wl.addWish(id, title, url, price, notes);
        return "redirect:/wishlist/" + id;
    }

    @GetMapping("/w/{slug}")
    public String publicView(@PathVariable String slug, Model model) {
        var opt = wl.findPublicBySlug(slug);
        if (opt.isEmpty()) return "login";
        Wishlist list = opt.get();
        model.addAttribute("list", list);
        model.addAttribute("wishes", list.getWishes());
        return "updateWish";
}
