CREATE TABLE role (
	role_id INTEGER NOT NULL AUTO_INCREMENT, 
	name VARCHAR(255), 
	PRIMARY KEY (role_id)
);

CREATE TABLE user (
	username VARCHAR(15) NOT NULL, 
	email VARCHAR(255) NOT NULL, 
	name VARCHAR(255) NOT NULL, 
	password VARCHAR(255) NOT NULL, 
	PRIMARY KEY (username)
);

CREATE TABLE user_role (
	username VARCHAR(255) NOT NULL, 
	role_id INTEGER NOT NULL, 
	PRIMARY KEY (username, role_id)
);

ALTER TABLE user_role ADD CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id) REFERENCES role (role_id);
ALTER TABLE user_role ADD CONSTRAINT fk_user_role_username FOREIGN KEY (username) REFERENCES user (username);

INSERT INTO ebook_chat.role VALUES (1,'ROLE_ADMIN');
INSERT INTO ebook_chat.role VALUES (2,'ROLE_USER');
INSERT INTO ebook_chat.user VALUES ('admin', 'admin@jorgeacetozi.com.br', 'Jorge Acetozi', '$2a$06$WfXHoFhYT/cXcyNOZQsjMuXRyydgcUTMJcMweF0m8RMub2HS1rCHu');
INSERT INTO ebook_chat.user_role VALUES ('admin', 1);
