CREATE TABLE app_user (
                          id BIGSERIAL PRIMARY KEY,
                          email TEXT NOT NULL UNIQUE,
                          password_hash TEXT NOT NULL,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE wishlist (
                          id BIGSERIAL PRIMARY KEY,
                          owner_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
                          title TEXT NOT NULL,
                          slug TEXT NOT NULL UNIQUE,
                          public_list BOOLEAN NOT NULL DEFAULT FALSE,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE wish (
                      id BIGSERIAL PRIMARY KEY,
                      wishlist_id BIGINT NOT NULL REFERENCES wishlist(id) ON DELETE CASCADE,
                      title TEXT NOT NULL,
                      url TEXT,
                      price NUMERIC(10,2),
                      notes TEXT,
                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
