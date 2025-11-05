package com.example.digitalwishlist.Controller;

import com.example.digitalwishlist.Service.WishListService;
import com.example.digitalwishlist.Model.WishList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WishListController {

    private final WishListService wl;

    public WishListController(WishListService wl) {
        this.wl = wl;
    }

    @GetMapping({"/", "/wishlist"})
    public String myLists(Model model,
                          @RequestParam(defaultValue = "") String owner) {

        model.addAttribute("owner", owner);
        model.addAttribute("lists", wl.byOwner(owner));

        return "wishlist"; // thymeleaf template
    }

    @PostMapping("/wishlist/create")
    public String createList(@RequestParam String owner,
                             @RequestParam String title) {

        wl.create(owner, title);
        return "redirect:/wishlist?owner=" + owner;
    }

    @GetMapping("/wishlist/{id}")
    public String viewList(@PathVariable Long id, Model model) {

        WishList list = wl.find(id);
        if(list == null) {
            return "redirect:/";
        }

        model.addAttribute("list", list);
        model.addAttribute("wishes", wl.wishesByList(id));

        return "addWish";
    }

    @PostMapping("/wishlist/{id}/wish")
    public String addWish(@PathVariable Long id,
                          @RequestParam String title,
                          @RequestParam(required = false) String url,
                          @RequestParam(required = false) Double price,
                          @RequestParam(required = false) String notes) {

        wl.addWish(id, title, url, price, notes);
        return "redirect:/wishlist/" + id;
    }

    @GetMapping("/w/{slug}")
    public String publicView(@PathVariable String slug, Model model) {

        WishList list = wl.publicBySlug(slug);
        if(list == null) return "redirect:/";

        model.addAttribute("list", list);
        model.addAttribute("wishes", wl.wishesPublic(slug));

        return "public";
    }
}
