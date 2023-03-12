create table kris_test.topic_posts (
  topic_id bigint not null,
  posts_id bigint not null,
  unique key uk_s5bd5c2jgcw49x5w4fiqmiagd (posts_id),
  key fkp3croykyaohnl662esakg61yj (topic_id),
  constraint fkitr4seeq9uagrsdo5e13tb8iw foreign key (posts_id) references post (id),
  constraint fkp3croykyaohnl662esakg61yj foreign key (topic_id) references topic (id)
);