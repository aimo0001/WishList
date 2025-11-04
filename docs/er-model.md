erDiagram

USER {
long id PK
string name
string email UK
}
WISHLIST {
long id PK
string title
long owner_id FK
string public_code UK
}
WISH {
long id PK
string name
string link_url
string description
double price
long wishlist_id FK
}

