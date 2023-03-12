create table kris_test.verification_token (
  id bigint not null auto_increment,
  token varchar(255) default null,
  user_user_id bigint default null,
  primary key (id),
  key fkdcmo89sbvh0wyky75gmscs8oy (user_user_id),
  constraint fkdcmo89sbvh0wyky75gmscs8oy foreign key (user_user_id) references user (user_id)
);