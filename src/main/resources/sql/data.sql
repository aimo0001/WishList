INSERT INTO app_user(name,email) VALUES ('Ole Bruger','ole@example.com') ON CONFLICT DO NOTHING;
INSERT INTO wishlist(title, owner_id, public_code)
SELECT 'Juleønsker', id, 'olecode' FROM app_user WHERE email='ole@example.com' ON CONFLICT DO NOTHING;
INSERT INTO wish(name,link_url,description,price,wishlist_id)
SELECT 'Strømper', null, 'Varme uldsokker', 79.95, w.id
FROM wishlist w
         JOIN app_user u ON w.owner_id=u.id
WHERE u.email='ole@example.com' AND w.title='Juleønsker'
    ON CONFLICT DO NOTHING;
