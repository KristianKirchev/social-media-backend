create table kris_test.post_report (
  id bigint not null auto_increment,
  report_status int default null,
  post_id bigint default null,
  user_id bigint default null,
  primary key (id),
  key fkeyehd7v09u9oxijrfvw1ufof (post_id),
  key fkqf8hfx9o3m0ym1aifmwjmycak (user_id),
  constraint fkeyehd7v09u9oxijrfvw1ufof foreign key (post_id) references post (id),
  constraint fkqf8hfx9o3m0ym1aifmwjmycak foreign key (user_id) references user (user_id)
);