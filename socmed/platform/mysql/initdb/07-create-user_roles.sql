create table kris_test.user_roles (
  user_id bigint not null,
  role_id bigint not null,
  primary key (user_id,role_id),
  key fkrhfovtciq1l558cw6udg0h0d3 (role_id),
  constraint fk55itppkw3i07do3h7qoclqd4k foreign key (user_id) references user (user_id),
  constraint fkrhfovtciq1l558cw6udg0h0d3 foreign key (role_id) references role (id)
);