create table kris_test.user (
  user_id bigint not null auto_increment,
  bio varchar(255) default null,
  date datetime(6) default null,
  email varchar(255) not null,
  is_enabled bit(1) not null,
  password varchar(255) not null,
  username varchar(255) not null,
  primary key (user_id),
  unique key uk_ob8kqyqqgmefl0aco34akdtpe (email),
  unique key uk_sb8bbouer5wak8vyiiy4pf2bx (username)
);