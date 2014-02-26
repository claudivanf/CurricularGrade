# --- Sample dataset

# --- !Ups

-- (id, nome, creditos, dificuldade, periodo_original)

-- periodo 1
insert into cadeira values(1,'Programação I',4,4,1);
insert into cadeira values(2,'Leitura e Prod. de Textos',4,2,1);
insert into cadeira values(3,'Cálculo I ',4,7,1);
insert into cadeira values(4,'Algebra Vetorial',4,3,1);
insert into cadeira values(5,'Int. à Computacação',4,5,1);
insert into cadeira values(6,'Lab. de Programação I',4,4,1);
-- periodo 2
insert into cadeira values(7,'Programação II',4,5,2);
insert into cadeira values(8,'Lab. de Programação II',4,5,2);
insert into cadeira values(9,'Matemática Discreta',4,5,2);
insert into cadeira values(10,'Metodologia Científica',4,4,2);
insert into cadeira values(11,'Teoria dos Grafos',2,3,2);
insert into cadeira values(12,'Fund. de Física Clássica',4,8,2);
insert into cadeira values(13,'Cálculo II',4,7,2);
-- periodo 3
insert into cadeira values(14,'Estrutura de Dados',4,7,3);
insert into cadeira values(15,'Lab. de Estrutura de Dados',4,7,3);
insert into cadeira values(16,'Algebra Linear',4,9,3);
insert into cadeira values(17,'Probabilidade e Est.',4,9,3);
insert into cadeira values(18,'Teoria da Computação',4,6,3);
insert into cadeira values(19,'Fund. de Física Moderna',4,7,3);
insert into cadeira values(20,'Gerência da Informação',4,5,3);
-- periodo 4
insert into cadeira values(21,'Métodos Estatísticos',4,5,4);
insert into cadeira values(22,'Paradigmas de Linguagens de Programação',2,5,4);
insert into cadeira values(23,'Org. e Arquitetura de Computadores I',4,5,4);
insert into cadeira values(24,'Lab. de Org. e Arquitetura de Computadores',4,5,4);
insert into cadeira values(25,'Lógica Matemática',4,5,4);
insert into cadeira values(26,'Sistemas de Informação I',4,5,4);
insert into cadeira values(27,'Engenharia de Software I',4,5,4);
-- periodo 5
insert into cadeira values(28,'Análise e Técnicas de Algoritmos',4,9,5);
insert into cadeira values(29,'Redes de Computadores',4,5,5);
insert into cadeira values(30,'Compiladores',4,9,5);
insert into cadeira values(31,'Banco de Dados I',4,5,5);
insert into cadeira values(32,'Sistemas de Informação II',4,5,5);
insert into cadeira values(33,'Laboratório de Engenharia de Software',4,5,5);
insert into cadeira values(34,'Informática e Sociedade',2,5,5);
-- periodo 6
insert into cadeira values(35,'Sistemas Operacionais',4,5,6);
insert into cadeira values(36,'Interconexão de Redes de Computadores',4,5,6);
insert into cadeira values(37,'Lab. de Interconexão de Redes de Computadores',4,5,6);
insert into cadeira values(38,'Inteligência Artificial I',4,5,6);
insert into cadeira values(39,'Banco de Dados II',4,5,6);
insert into cadeira values(40,'Direito e Cidadania',4,5,6);
insert into cadeira values(41,'Optativa 1',4,3,6);
insert into cadeira values(42,'Optativa 2',4,3,6);
-- periodo 7
insert into cadeira values(43,'Métodos e Software Númericos',4,5,7);
insert into cadeira values(44,'Aval. de Desempenho de Sist. Discretos',4,5,7);
insert into cadeira values(45,'Projeto em Computação I',4,5,7);
insert into cadeira values(46,'Optativa 3',4,3,7);
insert into cadeira values(47,'Optativa 4',4,3,7);
insert into cadeira values(48,'Optativa 5',4,3,7);
insert into cadeira values(49,'Optativa 6',4,3,7);
-- periodo 8
insert into cadeira values(50,'Projeto em Computação II',6,5,8);
insert into cadeira values(51,'Optativa 7',4,3,8);
insert into cadeira values(52,'Optativa 8',4,3,8);
insert into cadeira values(53,'Optativa 9',4,3,8);
insert into cadeira values(54,'Optativa 10',4,3,8);
insert into cadeira values(55,'Optativa 11',2,3,8);

-- relacionamentos
insert into cadeira_requisito values(7,1);
insert into cadeira_requisito values(7,5);
insert into cadeira_requisito values(7,6);
insert into cadeira_requisito values(8,1);
insert into cadeira_requisito values(8,5);
insert into cadeira_requisito values(8,6);
insert into cadeira_requisito values(11,1);
insert into cadeira_requisito values(11,6);
insert into cadeira_requisito values(12,3);
insert into cadeira_requisito values(12,4);
insert into cadeira_requisito values(13,3);
insert into cadeira_requisito values(14,7);
insert into cadeira_requisito values(14,8);
insert into cadeira_requisito values(14,11);
insert into cadeira_requisito values(15,7);
insert into cadeira_requisito values(15,8);
insert into cadeira_requisito values(15,11);
insert into cadeira_requisito values(16,4);
insert into cadeira_requisito values(17,13);
insert into cadeira_requisito values(18,5);
insert into cadeira_requisito values(18,9);
insert into cadeira_requisito values(18,11);
insert into cadeira_requisito values(19,12);
insert into cadeira_requisito values(19,13);
insert into cadeira_requisito values(21,16);
insert into cadeira_requisito values(21,17);
insert into cadeira_requisito values(22,18);
insert into cadeira_requisito values(22,14);
insert into cadeira_requisito values(22,15);
insert into cadeira_requisito values(23,14);
insert into cadeira_requisito values(23,15);
insert into cadeira_requisito values(23,19);
insert into cadeira_requisito values(24,14);
insert into cadeira_requisito values(24,15);
insert into cadeira_requisito values(24,19);
insert into cadeira_requisito values(25,18);
insert into cadeira_requisito values(26,20);
insert into cadeira_requisito values(27,17);
insert into cadeira_requisito values(27,7);
insert into cadeira_requisito values(27,8);
insert into cadeira_requisito values(28,13);
insert into cadeira_requisito values(28,14);
insert into cadeira_requisito values(28,15);
insert into cadeira_requisito values(28,25);
insert into cadeira_requisito values(29,23);
insert into cadeira_requisito values(29,24);
insert into cadeira_requisito values(30,22);
insert into cadeira_requisito values(30,23);
insert into cadeira_requisito values(30,24);
insert into cadeira_requisito values(31,26);
insert into cadeira_requisito values(32,26);
insert into cadeira_requisito values(33,27);
insert into cadeira_requisito values(35,23);
insert into cadeira_requisito values(35,24);
insert into cadeira_requisito values(36,29);
insert into cadeira_requisito values(37,29);
insert into cadeira_requisito values(38,21);
insert into cadeira_requisito values(38,22);
insert into cadeira_requisito values(38,28);
insert into cadeira_requisito values(39,31);
insert into cadeira_requisito values(39,32);
insert into cadeira_requisito values(43,16);
insert into cadeira_requisito values(43,28);
insert into cadeira_requisito values(44,17);
insert into cadeira_requisito values(45,10);
insert into cadeira_requisito values(45,33);
insert into cadeira_requisito values(50,45);
# --- !Downs

delete from cadeira;
delete from cadeira_requisito;