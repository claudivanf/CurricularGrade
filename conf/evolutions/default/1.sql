# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table cadeira (
  id                        bigint not null,
  nome                      varchar(255) not null,
  creditos                  integer,
  dificuldade               integer,
  periodo                   integer,
  plano_id                  bigint,
  constraint uq_cadeira_nome unique (nome),
  constraint pk_cadeira primary key (id))
;

create table plano_de_curso (
  id                        bigint not null,
  usuario_id                bigint,
  constraint pk_plano_de_curso primary key (id))
;

create table usuario (
  id                        bigint not null,
  nome                      varchar(255),
  senha                     varchar(255),
  plano_id                  bigint,
  constraint pk_usuario primary key (id))
;


create table cadeira_requisito (
  fk_cadeira                     bigint not null,
  fk_requisito                   bigint not null,
  constraint pk_cadeira_requisito primary key (fk_cadeira, fk_requisito))
;
create sequence cadeira_seq;

create sequence plano_de_curso_seq;

create sequence usuario_seq;

alter table cadeira add constraint fk_cadeira_plano_1 foreign key (plano_id) references plano_de_curso (id) on delete restrict on update restrict;
create index ix_cadeira_plano_1 on cadeira (plano_id);
alter table plano_de_curso add constraint fk_plano_de_curso_usuario_2 foreign key (usuario_id) references usuario (id) on delete restrict on update restrict;
create index ix_plano_de_curso_usuario_2 on plano_de_curso (usuario_id);
alter table usuario add constraint fk_usuario_plano_3 foreign key (plano_id) references plano_de_curso (id) on delete restrict on update restrict;
create index ix_usuario_plano_3 on usuario (plano_id);



alter table cadeira_requisito add constraint fk_cadeira_requisito_cadeira_01 foreign key (fk_cadeira) references cadeira (id) on delete restrict on update restrict;

alter table cadeira_requisito add constraint fk_cadeira_requisito_cadeira_02 foreign key (fk_requisito) references cadeira (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists cadeira;

drop table if exists cadeira_requisito;

drop table if exists plano_de_curso;

drop table if exists usuario;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists cadeira_seq;

drop sequence if exists plano_de_curso_seq;

drop sequence if exists usuario_seq;

