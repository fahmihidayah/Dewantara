# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table absensi (
  id_absensi                bigint auto_increment not null,
  date                      datetime not null,
  hadir                     tinyint(1) default 0 not null,
  GURU_NIK                  varchar(255),
  ID_MATAPELAJARAN          bigint,
  SISWA_NIM                 varchar(255),
  KETERANGAN                varchar(255),
  constraint pk_absensi primary key (id_absensi))
;

create table auth (
  id_auth                   bigint auto_increment not null,
  auth_token                varchar(255),
  constraint pk_auth primary key (id_auth))
;

create table guru (
  nik                       varchar(255) not null,
  name                      varchar(255),
  address                   varchar(255),
  phone                     varchar(255),
  email                     varchar(255),
  account_id                bigint,
  constraint pk_guru primary key (nik))
;

create table kelas (
  id_kelas                  bigint auto_increment not null,
  nama_kelas                varchar(255),
  constraint pk_kelas primary key (id_kelas))
;

create table mata_pelajaran (
  id_mata_pelajaran         bigint auto_increment not null,
  nama_mata_pelajaran       varchar(255),
  constraint pk_mata_pelajaran primary key (id_mata_pelajaran))
;

create table orang_tua (
  id_orang_tua              bigint auto_increment not null,
  name                      varchar(255) not null,
  address                   varchar(255) not null,
  phone_number              varchar(255) not null,
  constraint pk_orang_tua primary key (id_orang_tua))
;

create table siswa (
  nim                       varchar(255) not null,
  name                      varchar(255),
  address                   varchar(255),
  kelas_id_kelas            bigint,
  orang_tua_id_orang_tua    bigint,
  constraint pk_siswa primary key (nim))
;

create table user (
  id                        bigint auto_increment not null,
  user_name                 varchar(256) not null,
  sha_password              varbinary(64) not null,
  password                  varchar(255),
  type                      varchar(255),
  constraint uq_user_user_name unique (user_name),
  constraint pk_user primary key (id))
;

alter table absensi add constraint fk_absensi_guru_1 foreign key (GURU_NIK) references guru (nik) on delete restrict on update restrict;
create index ix_absensi_guru_1 on absensi (GURU_NIK);
alter table absensi add constraint fk_absensi_mataPelajaran_2 foreign key (ID_MATAPELAJARAN) references mata_pelajaran (id_mata_pelajaran) on delete restrict on update restrict;
create index ix_absensi_mataPelajaran_2 on absensi (ID_MATAPELAJARAN);
alter table absensi add constraint fk_absensi_siswa_3 foreign key (SISWA_NIM) references siswa (nim) on delete restrict on update restrict;
create index ix_absensi_siswa_3 on absensi (SISWA_NIM);
alter table guru add constraint fk_guru_account_4 foreign key (account_id) references user (id) on delete restrict on update restrict;
create index ix_guru_account_4 on guru (account_id);
alter table siswa add constraint fk_siswa_kelas_5 foreign key (kelas_id_kelas) references kelas (id_kelas) on delete restrict on update restrict;
create index ix_siswa_kelas_5 on siswa (kelas_id_kelas);
alter table siswa add constraint fk_siswa_orangTua_6 foreign key (orang_tua_id_orang_tua) references orang_tua (id_orang_tua) on delete restrict on update restrict;
create index ix_siswa_orangTua_6 on siswa (orang_tua_id_orang_tua);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table absensi;

drop table auth;

drop table guru;

drop table kelas;

drop table mata_pelajaran;

drop table orang_tua;

drop table siswa;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

