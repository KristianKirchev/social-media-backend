create table kris_test.message (
  id bigint not null auto_increment,
  content varchar(255) not null,
  seen_at datetime(6) default null,
  sent_at datetime(6) default null,
  from_user_id bigint not null,
  to_user_id bigint not null,
  primary key (id),
  key fk3nju8asf4v72h0d7g6vgtx7p2 (from_user_id),
  key fkgm8awic1hpa2cgr7pv7j8yn0 (to_user_id),
  constraint fk3nju8asf4v72h0d7g6vgtx7p2 foreign key (from_user_id) references user (user_id),
  constraint fkgm8awic1hpa2cgr7pv7j8yn0 foreign key (to_user_id) references user (user_id)
);