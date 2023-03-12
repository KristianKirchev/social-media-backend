create table kris_test.notification (
  id bigint not null auto_increment,
  date datetime(6) default null,
  notification_type int default null,
  marked_as_read bit(1) default null,
  from_user_id bigint not null,
  post_id bigint default null,
  to_user_id bigint not null,
  primary key (id),
  key fkqq8sa8nn030wp2n9chd9mw2kh (from_user_id),
  key fkn1l10g2mvj4r1qs93k952fshe (post_id),
  key fk1u6860t77y9lea8nl3jq6yyjw (to_user_id),
  constraint fk1u6860t77y9lea8nl3jq6yyjw foreign key (to_user_id) references user (user_id),
  constraint fkn1l10g2mvj4r1qs93k952fshe foreign key (post_id) references post (id) on delete cascade,
  constraint fkqq8sa8nn030wp2n9chd9mw2kh foreign key (from_user_id) references user (user_id)
);