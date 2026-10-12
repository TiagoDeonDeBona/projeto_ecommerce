insert into tb_category (name) values ('Informática');
insert into tb_category (name) values ('UD');
insert into tb_category (name) values ('Cozinha');
insert into tb_category (name) values ('Móveis');
insert into tb_category (name) values ('Eletrônico');

insert into tb_product(name, description, price, category_id) values ( 'Notebook Arus 15.6','Notebook Arus 15.6 Core I7, 16Gb Ram...',2449.0,1);
insert into tb_product(name, description, price, category_id) values ( 'Monitor 27pol','Monitor Gamer 27pol 144Hz, 1ms',1129.99,1);
insert into tb_product(name, description, price, category_id) values ( 'Refrigerador 429L','Refrigerador 429L Branco, duplex....',1990.0,2);
insert into tb_product(name, description, price, category_id) values ( 'Kit Teclado e Mouse','Kit com teclado ABNT e mouse com 5 botões',199.0,1);
insert into tb_product(name, description, price, category_id) values ( 'Roteador Wi-Fi 5.4GhZ','Roteador Wi-Fi 5.4GhZ, 6 antenas...',1299.0,1);
insert into tb_product(name, description, price, category_id) values ( 'Smartphone XYZ','Smatphone com tela de 9pol, 12GB....',9999.0,5);
insert into tb_product(name, description, price, category_id) values ( 'TV LCD 75pol','TV LCD 75pol, 5 HDMI...',7555.0,5);
insert into tb_product(name, description, price, category_id) values ( 'Fogão 6 Bocas','Fogão 6 Bocas em aço inox, ...', 799.99,3);

INSERT INTO tb_user(username, email, password) VALUES ('admin', 'admin@example.com','$2a$10$.PVIfB07x.SfMYTcToxL0.yxcLWU0GbS2NUO1W1QAvqMm/TsFhVem');
INSERT INTO tb_user(username, email, password) VALUES ('test', 'test@example.com','$2a$10$.PVIfB07x.SfMYTcToxL0.yxcLWU0GbS2NUO1W1QAvqMm/TsFhVem');

insert into tb_address(user_id, cep, rua, numero, complemento, estado, cidade, regiao, uf, ddd, nome_recebimento, telefone_contato) values (1, '85501000', 'Rua Tocantins', '150', 'Casa', 'Paraná', 'Pato Branco', 'Sul', 'PR', '46', 'Tiago', '46999990001');
insert into tb_address(user_id, cep, rua, numero, complemento, estado, cidade, regiao, uf, ddd, nome_recebimento, telefone_contato) values (1, '89990000', 'Rua dos Ipês', 'S/N', 'Chácara', 'Santa Catarina', 'São Lourenço do Oeste', 'Sul', 'SC', '49', 'Tiago', '49999990002');
insert into tb_address(user_id, cep, rua, numero, complemento, estado, cidade, regiao, uf, ddd, nome_recebimento, telefone_contato) values (1, '85503000', 'Rua Itacolomi', '325', 'Apartamento 2', 'Paraná', 'Pato Branco', 'Sul', 'PR', '46', 'Tiago', '46999990003');
insert into tb_address(user_id, cep, rua, numero, complemento, estado, cidade, regiao, uf, ddd, nome_recebimento, telefone_contato) values (2, '80010000', 'Rua XV de Novembro', '450', 'Casa', 'Paraná', 'Curitiba', 'Sul', 'PR', '41', 'Cliente Teste', '41999990001');
insert into tb_address(user_id, cep, rua, numero, complemento, estado, cidade, regiao, uf, ddd, nome_recebimento, telefone_contato) values (2, '88010000', 'Rua Felipe Schmidt', '200', 'Sala 3', 'Santa Catarina', 'Florianópolis', 'Sul', 'SC', '48', 'Cliente Teste', '48999990002');
insert into tb_address(user_id, cep, rua, numero, complemento, estado, cidade, regiao, uf, ddd, nome_recebimento, telefone_contato) values (2, '90010000', 'Rua dos Andradas', '780', 'Apartamento 5', 'Rio Grande do Sul', 'Porto Alegre', 'Sul', 'RS', '51', 'Cliente Teste', '51999990003');

