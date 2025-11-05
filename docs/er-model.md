erDiagram
WISHLIST ||--o{ WISH : contains

    WISHLIST {
        bigint id PK
        varchar title
        varchar description
    }

    WISH {
        bigint id PK
        varchar name
        varchar description
        varchar link
        double price
        bigint wishlist_id FK
    }
