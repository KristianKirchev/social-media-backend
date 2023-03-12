create table kris_test.topic (
  id bigint not null auto_increment,
  date datetime(6) default null,
  description varchar(255) default null,
  name varchar(255) default null,
  user_user_id bigint default null,
  primary key (id),
  unique key uk_mbunn9erv8nmf5lk1r2nu0nex (name),
  key fkevuv7p02842lhi77hguyhcgos (user_user_id),
  constraint fkevuv7p02842lhi77hguyhcgos foreign key (user_user_id) references user (user_id)
);