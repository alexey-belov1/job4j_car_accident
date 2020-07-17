create table accident (
  id serial primary key,
  name varchar(200),
  text varchar(200),
  address varchar(200)
);

create table users (
  username varchar(50) not null,
  password varchar(100) not null,
  enabled boolean default true,
  primary key (username)
);

create table authorities (
  username varchar(50) not null,
  authority varchar(50) not null,
  foreign key (username) references users(username)
);