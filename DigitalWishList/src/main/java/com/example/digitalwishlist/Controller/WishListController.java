package com.example.digitalwishlist.Controller;

import com.example.digitalwishlist.Model.Wishlist;
import com.example.digitalwishlist.Service.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class WishListController {

    private final WishListService wl;

    public WishListController(WishListService wl) { this.wl = wl; }

    @GetMapping({"/", "/wishlist"})
    public String myLists(@RequestParam(required = false) String owner, Model model) {
        model.addAttribute("owner", owner);
        model.addAttribute("lists", (owner == null || owner.isBlank()) ? java.util.List.of() : wl.mine(owner));
        return "wishlist";
    }

    @PostMapping("/wishlist/create")
    public String createList(@RequestParam String owner,
                             @RequestParam String title,
                             @RequestParam(defaultValue = "false") boolean isPublic) {
        wl.create(owner, title, isPublic);
        return "redirect:/wishlist?owner=" + owner;
    }

    @GetMapping("/wishlist/{id}")
    public String viewList(@PathVariable Long id,
                           @RequestParam String owner,
                           Model model) {
        Wishlist list = wl.mine(owner).stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("owner", owner);
        model.addAttribute("list", list);
        model.addAttribute("wishes", list.getWishes());
        return "addWish";
    }

    @PostMapping("/wishlist/{id}/wish")
    public String addWish(@PathVariable Long id,
                          @RequestParam String owner,
                          @RequestParam String title,
                          @RequestParam(required = false) String url,
                          @RequestParam(required = false) Double price,
                          @RequestParam(required = false) String notes) {
        wl.addWish(id, title, url, price, notes);
        return "redirect:/wishlist/" + id + "?owner=" + owner;
    }

    @GetMapping("/w/{slug}")
    public String publicView(@PathVariable String slug, Model model) {
        Wishlist list = wl.findPublicBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("list", list);
        model.addAttribute("wishes", list.getWishes());
        return "public";
    }
}
