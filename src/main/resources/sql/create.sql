CREATE TABLE IF NOT EXISTS app_user (
                                        id SERIAL PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
    );
CREATE TABLE IF NOT EXISTS wishlist (
                                        id SERIAL PRIMARY KEY,
                                        title VARCHAR(255) NOT NULL,
    owner_id INTEGER REFERENCES app_user(id) ON DELETE CASCADE,
    public_code VARCHAR(255) UNIQUE NOT NULL
    );
CREATE TABLE IF NOT EXISTS wish (
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL,
    link_url TEXT,
    description TEXT,
    price NUMERIC,
    wishlist_id INTEGER REFERENCES wishlist(id) ON DELETE CASCADE
    );
