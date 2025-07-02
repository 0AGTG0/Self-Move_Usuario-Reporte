create database if not exists selfMove_Usuario_Reporte;

use selfMove_Usuario_Reporte;

create table if not exists usuario(
	id_usuario int auto_increment,
    nombre varchar (50) not null,
    direccion varchar (100) not null,
    correo varchar (40) not null unique,
    contrasenia_hash varchar (255) not null,
    activo boolean default true,
    fecha_hora_registro datetime default current_timestamp,
    constraint usuario_id_usuario_pk primary key (id_usuario)
);

-- current_timestamp es para poner el timepo actual

create table if not exists tipo_reporte (
	id_tipo int auto_increment,
    nombre_tipo varchar (50) not null unique,
    constraint tipo_reporte_id_tipo_pk primary key(id_tipo)
);
INSERT INTO tipo_reporte (nombre_tipo) VALUES ('Queja');
INSERT INTO tipo_reporte (nombre_tipo) VALUES ('Sugerencia');
INSERT INTO tipo_reporte (nombre_tipo) VALUES ('Retraso');

create table if not exists estado_reporte (
	id_estado int auto_increment,
    nombre_estado varchar (50) not null unique,
    constraint estado_reporte_id_estado_pk primary key(id_estado)
);
INSERT INTO estado_reporte (nombre_estado) VALUES ('Pendiente');
INSERT INTO estado_reporte (nombre_estado) VALUES ('En revision');
INSERT INTO estado_reporte (nombre_estado) VALUES ('Resuelto');
INSERT INTO estado_reporte (nombre_estado) VALUES ('Descartado');

create table if not exists reporte(
	folio int auto_increment,
    id_usuario int null,
    id_tipo int not null,
    id_estado int not null,
    fecha_hora_reporte datetime default current_timestamp not null,
    contenido text not null,
	constraint reporte_folio_pk primary key (folio),
    constraint reporte_id_usuario_fk foreign key (id_usuario) references usuario (id_usuario)
		on delete set null -- Si un usuario es eliminado, sus reportes permanecen pero se marcan como de un usuario NULL
		on update cascade,
    constraint reporte_id_tipo_fk foreign key (id_tipo) references tipo_reporte (id_tipo)
		on update cascade,
    constraint reporte_id_estado_fk foreign key (id_estado) references estado_reporte (id_estado)
        on update cascade -- Si el ID del usuario cambia la FK se actualiza
);