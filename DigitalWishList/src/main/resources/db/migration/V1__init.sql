CREATE TABLE IF NOT EXISTS wishlist (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        owner_name VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    public_list BOOLEAN NOT NULL,
    created_at TIMESTAMP(6) NOT NULL
    );

CREATE INDEX idx_wishlist_owner ON wishlist(owner_name);

CREATE TABLE IF NOT EXISTS wish (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    wishlist_id BIGINT NOT NULL,
                                    title VARCHAR(255) NOT NULL,
    url VARCHAR(1000),
    price DOUBLE,
    notes VARCHAR(1000),
    created_at TIMESTAMP(6) NOT NULL,
    CONSTRAINT fk_wish_wishlist FOREIGN KEY (wishlist_id) REFERENCES wishlist(id)
    );

CREATE INDEX idx_wish_wishlist ON wish(wishlist_id);
