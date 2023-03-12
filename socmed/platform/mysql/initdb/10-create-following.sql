create table kris_test.following (
  followed_user_id bigint not null,
  following_user_id bigint not null,
  date datetime(6) default null,
  primary key (followed_user_id,following_user_id),
  key fkhnf5bu72nsc7ov7ovsdoq5ykt (following_user_id),
  constraint fkhnf5bu72nsc7ov7ovsdoq5ykt foreign key (following_user_id) references user (user_id),
  constraint fkjmyjyxkmthtw6bu6q75bn6cjc foreign key (followed_user_id) references user (user_id)
);