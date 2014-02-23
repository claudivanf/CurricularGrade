# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table cadeira (
  id                        bigint not null,
  nome                      varchar(255) not null,
  creditos                  integer,
  dificuldade               integer,
  periodo_id                bigint,
  constraint uq_cadeira_nome unique (nome),
  constraint pk_cadeira primary key (id))
;

create table periodo (
  id                        bigint not null,
  numero_periodo            integer,
  constraint pk_periodo primary key (id))
;

create table plano_de_curso (
  id                        bigint not null,
  constraint pk_plano_de_curso primary key (id))
;


create table cadeira_requisito (
  fk_cadeira                     bigint not null,
  fk_requisito                   bigint not null,
  constraint pk_cadeira_requisito primary key (fk_cadeira, fk_requisito))
;

create table plano_periodo (
  fk_plano_id                    bigint not null,
  fk_periodo_id                  bigint not null,
  constraint pk_plano_periodo primary key (fk_plano_id, fk_periodo_id))
;
create sequence cadeira_seq;

create sequence periodo_seq;

create sequence plano_de_curso_seq;

alter table cadeira add constraint fk_cadeira_periodo_1 foreign key (periodo_id) references periodo (id) on delete restrict on update restrict;
create index ix_cadeira_periodo_1 on cadeira (periodo_id);



alter table cadeira_requisito add constraint fk_cadeira_requisito_cadeira_01 foreign key (fk_cadeira) references cadeira (id) on delete restrict on update restrict;

alter table cadeira_requisito add constraint fk_cadeira_requisito_cadeira_02 foreign key (fk_requisito) references cadeira (id) on delete restrict on update restrict;

alter table plano_periodo add constraint fk_plano_periodo_plano_de_cur_01 foreign key (fk_plano_id) references plano_de_curso (id) on delete restrict on update restrict;

alter table plano_periodo add constraint fk_plano_periodo_periodo_02 foreign key (fk_periodo_id) references periodo (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists cadeira;

drop table if exists cadeira_requisito;

drop table if exists periodo;

drop table if exists plano_de_curso;

drop table if exists plano_periodo;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists cadeira_seq;

drop sequence if exists periodo_seq;

drop sequence if exists plano_de_curso_seq;

