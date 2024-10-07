create table tb_cidade (
id_cidade bigint not null primary key,
nome varchar(50) not null,
qtd_habitantes bigint );

insert into tb_cidade (id_cidade, nome, qtd_habitantes) values
(1, 'São Paulo', 12396372),
(2, 'Rio de Janeiro', 1000000),
(3, 'Maranhao', 148540),
(4, 'Amazonas', 12396372),
(5, 'Rio Grande do Norte', 1000000),
(6, 'Mato Grosso do Sul', 148540),
(7, 'Minas Gerais', 12396372),
(8, 'Goias', 1000000),
(9, 'Espirito Santo', 148540),
(10, 'Acre', 12396372),
(11, 'Roraima', 1000000),
(12, 'Rondonia', 148540);