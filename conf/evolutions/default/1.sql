# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table cadeira (
  id                        bigint not null,
  nome                      varchar(255),
  creditos                  integer,
  dificuldade               integer,
  periodo_original          integer,
  constraint pk_cadeira primary key (id))
;

create table periodo (
  id                        bigint not null,
  constraint pk_periodo primary key (id))
;

create table plano_de_curso (
  id                        bigint not null,
  periodo_atual             integer,
  constraint pk_plano_de_curso primary key (id))
;

create table usuario (
  email                     varchar(255) not null,
  nome                      varchar(255),
  senha                     varchar(255),
  plano_id                  bigint,
  periodo_atual             integer,
  constraint pk_usuario primary key (email))
;


create table cadeira_requisito (
  id_disciplina                  bigint not null,
  id_requisito                   bigint not null,
  constraint pk_cadeira_requisito primary key (id_disciplina, id_requisito))
;

create table periodo_cadeira (
  fk_periodo                     bigint not null,
  fk_cadeira                     bigint not null,
  constraint pk_periodo_cadeira primary key (fk_periodo, fk_cadeira))
;

create table plano_periodo (
  fk_plano                       bigint not null,
  fk_periodo                     bigint not null,
  constraint pk_plano_periodo primary key (fk_plano, fk_periodo))
;
create sequence cadeira_seq;

create sequence periodo_seq;

create sequence plano_de_curso_seq;

create sequence usuario_seq;

alter table usuario add constraint fk_usuario_plano_1 foreign key (plano_id) references plano_de_curso (id);
create index ix_usuario_plano_1 on usuario (plano_id);



alter table cadeira_requisito add constraint fk_cadeira_requisito_cadeira_01 foreign key (id_disciplina) references cadeira (id);

alter table cadeira_requisito add constraint fk_cadeira_requisito_cadeira_02 foreign key (id_requisito) references cadeira (id);

alter table periodo_cadeira add constraint fk_periodo_cadeira_periodo_01 foreign key (fk_periodo) references periodo (id);

alter table periodo_cadeira add constraint fk_periodo_cadeira_cadeira_02 foreign key (fk_cadeira) references cadeira (id);

alter table plano_periodo add constraint fk_plano_periodo_plano_de_cur_01 foreign key (fk_plano) references plano_de_curso (id);

alter table plano_periodo add constraint fk_plano_periodo_periodo_02 foreign key (fk_periodo) references periodo (id);

# --- !Downs

drop table if exists cadeira cascade;

drop table if exists cadeira_requisito cascade;

drop table if exists periodo cascade;

drop table if exists periodo_cadeira cascade;

drop table if exists plano_de_curso cascade;

drop table if exists plano_periodo cascade;

drop table if exists usuario cascade;

drop sequence if exists cadeira_seq;

drop sequence if exists periodo_seq;

drop sequence if exists plano_de_curso_seq;

drop sequence if exists usuario_seq;

