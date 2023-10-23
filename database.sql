use belajar_spring_restful_api;
--create table contacts (
--    id varchar(100) not null ,
--    username varchar (100) not null ,
--    first_name varchar(100) not null ,
--    last_name varchar(100),
--    phone varchar(100) ,
--    email varchar(100),
--    primary key (id),
--    foreign key fk_users_contacts (username) references users(username)
--    )engine InnoDB;

-- CREATE TABLE users (
--     username varchar(100) not null ,
--     password varchar(100) not null ,
--     name varchar(100) not null ,
--     token varchar(100),
--     token_expired_at bigint ,
--     primary key (username),
--     unique (token)
-- )engine InnoDB;
--create table addresses (
--    id varchar(100) not null ,
--    contact_id varchar(200) not null ,
--    street varchar(200) ,
--    city varchar(100) ,
--    country varchar(100) not null,
--    postal_code varchar(10),
--    primary key (id),
--    foreign key fk_contacts_addresses (contact_id) references contacts (id)
--)engine InnoDB;
select * from users;
truncate users;
select * from contacts;
select * from addresses;

