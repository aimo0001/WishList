Klasse diagram

```mermaid
classDiagram

class Wishlist {
+Long id
+String title
+String description
+List~Wish~ wishes
+void addWish(Wish wish)
}

    class Wish {
        +Long id
        +String name
        +String description
        +String link
        +Double price
        +Wishlist wishlist
    }

    class WishListController {
        +List~Wishlist~ getAllWishlists()
        +Wishlist getWishlistById(Long id)
        +Wishlist createWishlist(Wishlist wishlist)
        +void deleteWishlist(Long id)
    }

    class WishListService {
        +List~Wishlist~ findAll()
        +Wishlist findById(Long id)
        +Wishlist save(Wishlist wishlist)
        +void delete(Long id)
    }

    class WishListRepository {
        <<interface>>
        +List~Wishlist~ findAll()
        +Optional~Wishlist~ findById(Long id)
        +Wishlist save(Wishlist wishlist)
        +void deleteById(Long id)
    }

    class WishRepository {
        <<interface>>
        +List~Wish~ findAll()
        +Optional~Wish~ findById(Long id)
        +Wish save(Wish wish)
        +void deleteById(Long id)
    }

    Wishlist "1" --> "*" Wish : indeholder
    WishListController --> WishListService : kalder
    WishListService --> WishListRepository : anvender
    WishListService --> WishRepository : anvender
