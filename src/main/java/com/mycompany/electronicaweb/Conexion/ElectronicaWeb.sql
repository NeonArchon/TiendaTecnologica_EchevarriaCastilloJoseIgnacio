-- borrar la DB si es necesario
-- DROP DATABASE IF EXISTS ElectronicaWeb;

-- Crear Base de Datos
CREATE DATABASE ElectronicaWeb;

USE ElectronicaWeb;

CREATE TABLE categoria (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE producto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    inventario INT
);

CREATE TABLE caracteristica (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    producto_id INT,
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);

CREATE TABLE imagen (
    id INT PRIMARY KEY AUTO_INCREMENT,
    url VARCHAR(255) NOT NULL,
    producto_id INT,
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);

CREATE TABLE usuario (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE direccion (
    id INT PRIMARY KEY AUTO_INCREMENT,
    calle VARCHAR(255) NOT NULL,
    numero VARCHAR(50) NOT NULL,
    ciudad VARCHAR(255) NOT NULL,
    pais VARCHAR(255) NOT NULL,
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE historial_compras (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    fecha_compra DATE NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);