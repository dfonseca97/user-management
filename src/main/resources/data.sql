INSERT IGNORE INTO roles(name) VALUES('ROLE_ADMIN');
INSERT IGNORE INTO roles(name) VALUES('ROLE_USER');
INSERT IGNORE INTO users(full_name,full_business_title,address,email,active,password) VALUES('ADMIN','ADMIN','ADMIN','admin@admin.admin',1,'$2a$10$cJ4ZUx.IF3csa1xgpGVGhO/njTjKKrkGga9Pl8rZdCzSj7B/gf4he');
INSERT IGNORE INTO user_roles(user_id,role_id) VALUES(1,1);
