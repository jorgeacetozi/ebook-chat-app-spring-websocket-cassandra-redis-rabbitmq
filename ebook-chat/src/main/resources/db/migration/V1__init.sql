create table role (role_id integer not null auto_increment, name varchar(255), primary key (role_id));
create table user (username varchar(15) not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (username));
create table user_role (username varchar(255) not null, role_id integer not null, primary key (username, role_id));
alter table user_role add constraint FKa68196081fvovjhkek5m97n3y foreign key (role_id) references role (role_id);
alter table user_role add constraint FKnircs1pyebpo0eucojumm0aed foreign key (username) references user (username);

INSERT INTO ebook_chat.role VALUES (1,'ROLE_ADMIN');
INSERT INTO ebook_chat.role VALUES (2,'ROLE_USER');
INSERT INTO ebook_chat.user VALUES ('admin', 'admin@jorgeacetozi.com.br', 'Jorge Acetozi', '$2a$06$WfXHoFhYT/cXcyNOZQsjMuXRyydgcUTMJcMweF0m8RMub2HS1rCHu');
INSERT INTO ebook_chat.user_role VALUES ('admin', 1);
