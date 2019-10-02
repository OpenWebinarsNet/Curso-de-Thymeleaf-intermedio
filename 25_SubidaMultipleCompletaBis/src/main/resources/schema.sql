drop table categoria if exists;
drop table producto if exists;
drop table puntuacion if exists;
drop table oferta if exists;
drop table usuario if exists;
drop table favoritos if exists;
drop table producto_imagen if exists;

drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start with 100 increment by 1;

create table categoria (
	id bigint not null, 
	destacada boolean not null, 
	imagen varchar(512), 
	nombre varchar(512), 
	primary key (id)
);

create table producto (
	id bigint not null, 
	descripcion clob,   
	nombre varchar(512), 
	pvp float not null, 
	disponibilidad varchar2(50),
	categoria_id bigint, 
	primary key (id)
);

create table oferta (
	id bigint not null, 
	nombre varchar(512),
	nuevo_pvp float,
	porcentaje_descuento float,
	fecha_inicio timestamp,
	fecha_fin timestamp,
	producto_id bigint,
	primary key (id)
);

create table puntuacion (
	id bigint not null, 
	fecha timestamp, 
	puntuacion integer not null, 
	producto_id bigint, 
	primary key (id)
);

create table usuario (
	id bigint not null, 
	nombre varchar(256),
	username varchar(128),
	password varchar(512),
	rol varchar(128),
	primary key (id)
);

create table favoritos (
	usuario_id bigint,
	producto_id bigint,
	primary key (usuario_id, producto_id)
);

create table producto_imagen (
	producto_id bigint not null, 
	imagen varchar(512)
);



alter table producto add constraint fk_producto_categoria foreign key (categoria_id) references categoria;
alter table puntuacion add constraint fk_puntuacion_producto foreign key (producto_id) references producto;
alter table oferta add constraint fk_oferta_producto foreign key (producto_id) references producto;
alter table favoritos add constraint fk_fav_usu foreign key (usuario_id) references usuario;
alter table favoritos add constraint fk_fav_prod foreign key (producto_id) references producto;
alter table producto_imagen add constraint fk_imagen_producto foreign key (producto_id) references producto;
