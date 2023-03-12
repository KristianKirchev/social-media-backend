create table kris_test.comment (
  id bigint not null auto_increment,
  content varchar(255) default null,
  date datetime(6) not null,
  post_id bigint not null,
  user_id bigint not null,
  primary key (id),
  key fks1slvnkuemjsq2kj4h3vhx7i1 (post_id),
  key fk8kcum44fvpupyw6f5baccx25c (user_id),
  constraint fk8kcum44fvpupyw6f5baccx25c foreign key (user_id) references user (user_id),
  constraint fks1slvnkuemjsq2kj4h3vhx7i1 foreign key (post_id) references post (id)
);