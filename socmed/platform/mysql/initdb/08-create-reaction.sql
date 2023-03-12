create table kris_test.reaction (
  id bigint not null auto_increment,
  reaction_type int default null,
  post_id bigint not null,
  user_id bigint not null,
  primary key (id),
  key fkathfhl7fif9f9mggdjhg7ktdt (post_id),
  key fkp68qgeq3telx6adl7hssrdxbw (user_id),
  constraint fkathfhl7fif9f9mggdjhg7ktdt foreign key (post_id) references post (id) on delete cascade,
  constraint fkp68qgeq3telx6adl7hssrdxbw foreign key (user_id) references user (user_id)
);