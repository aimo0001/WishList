# Klassediagram

```mermaid
classDiagram
    class User { 
        +Long id
        +String name
        +String email
        +List~Wishlist~ wishlists
    }
    class Wishlist {
        +Long id
        +String title
        +String publicCode
        +User owner
        +List~Wish~ wishes
    }
    class Wish {
        +Long id
        +String name
        +String linkUrl
        +String description
        +Double price
        +Wishlist wishlist
    }

    User "1" --> "*" Wishlist : owns
    Wishlist "1" --> "*" Wish : contains

