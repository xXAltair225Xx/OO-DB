CREATE TABLE if not exists Giocatore(

	id_giocatore SERIAL not null,
	nome Varchar(50) not null,
	cognome Varchar(50) not null,
	data_nascita date not null,
	piede Varchar(12) not null,
	trofei int  not null default 0,
	data_ritiro date default NULL,
	login Varchar(255) not NULL unique,
	password_giocatore Varchar(255) not null,
	PRIMARY KEY(id_giocatore)

);

----------------------------------------------

INSERT INTO giocatore (nome, cognome, data_nascita, piede, trofei, data_ritiro, login, password_giocatore)
VALUES ('Cristiano','Ronaldo', '1985-02-05', 'Destro', 34, NULL, 'Cristian_Ronaldo85', 'PiuMeloMeno');

----------------------------------------------
CREATE TABLE if not exists Squadra(

	id_squadra SERIAL not null,
	nome varchar(50) not null unique ,
	nazionalita varchar(50) not null,
	trofei int not null DEFAULT 0,
	PRIMARY KEY (id_squadra)
);
----------------------------------------------

CREATE TABLE Ruolo(

	id_ruolo SERIAL not NULL,
	nome_ruolo Varchar(50) not NULL,
	PRIMARY KEY (id_ruolo)
);

----------------------------------------------

SELECT giocatore.nome , giocatore.cognome, ruolo.nome_Ruolo
FROM giocatore 
INNER JOIN ruolo on giocatore.id_ruolo = ruolo.id_ruolo;

----------------------------------------------