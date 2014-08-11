# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table siswa (
  nim                       varchar(255) not null,
  name                      varchar(255),
  address                   varchar(255),
  constraint pk_siswa primary key (nim))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table siswa;

SET FOREIGN_KEY_CHECKS=1;

