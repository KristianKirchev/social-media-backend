create table kris_test.post (
  id bigint not null auto_increment,
  content longblob,
  date datetime(6) default null,
  deleteb_by_admin datetime(6) default null,
  title varchar(255) default null,
  topic_id bigint default null,
  user_id bigint not null,
  primary key (id),
  key fkg8ln3nj8tjclai0nuvpw5s5uw (topic_id),
  key fk72mt33dhhs48hf9gcqrq4fxte (user_id),
  constraint fk72mt33dhhs48hf9gcqrq4fxte foreign key (user_id) references user (user_id),
  constraint fkg8ln3nj8tjclai0nuvpw5s5uw foreign key (topic_id) references topic (id)
);