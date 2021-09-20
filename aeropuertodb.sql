
CREATE TABLE Aerolineas (
   idAerolinea INT NOT NULL,
   nombre VARCHAR(45) NULL,
   usuario VARCHAR(45) NULL,
   contrasena VARCHAR(45) NULL,
   email  VARCHAR(45) NULL,
   estado VARCHAR(45) NULL,
  PRIMARY KEY (idAerolinea));

CREATE TABLE Pilotos (
   idPiloto INT NOT NULL,
   nombre VARCHAR(45) NULL,
   apellido VARCHAR(45) NULL,
   licencia VARCHAR(45) NULL,
   horasVuelo VARCHAR(45) NULL,
   revisionMedica VARCHAR(45) NULL,
   idAerolinea INT NOT NULL,
  PRIMARY KEY (idPiloto),
  CONSTRAINT  fk_Piloto_Aerolinea
    FOREIGN KEY (idAerolinea)
    REFERENCES Aerolineas (idAerolinea));

CREATE TABLE Aviones (
   idAvion INT NOT NULL,
   tipo VARCHAR(45) NULL,
   capacidad VARCHAR(45) NULL,
   modelo VARCHAR(45) NULL,
   tipoPropulsion VARCHAR(45) NULL,
   pesoNominal VARCHAR(45) NULL,
   numeroMotores VARCHAR(45) NULL,
   idAerolinea INT NOT NULL,
   PRIMARY KEY (idAvion),
   CONSTRAINT fk_Avion_Aerolinea1
    FOREIGN KEY (idAerolinea)
    REFERENCES  Aerolineas (idAerolinea));


CREATE TABLE Usuarios (
   idUsuario INT NOT NULL,
   nombre VARCHAR(45) NULL,
   usuario VARCHAR(45) NULL,
   contrasena VARCHAR(45) NULL,
   rol VARCHAR(45) NULL,
   PRIMARY KEY (idUsuario));

CREATE TABLE Vuelos (
   idVuelo INT NOT NULL,
   hora VARCHAR(45) NULL,
   fecha VARCHAR(45) NULL,
   tipo VARCHAR(45) NULL,
   estado VARCHAR(45) NULL,
   destino VARCHAR(45) NULL,
   procedencia VARCHAR(45) NULL,
   idPiloto INT NOT NULL,
   idAerolinea INT NOT NULL,
   idAvion INT NOT NULL,
   idcopiloto VARCHAR(45) NOT NULL,
  PRIMARY KEY (idVuelo),
  CONSTRAINT fk_Vuelo_Piloto1
    FOREIGN KEY (idPiloto)
    REFERENCES Pilotos (idPiloto),
  CONSTRAINT fk_Vuelo_Aerolinea1
    FOREIGN KEY (idAerolinea)
    REFERENCES Aerolineas (idAerolinea),
  CONSTRAINT fk_Vuelo_Avion1
    FOREIGN KEY (idAvion)
    REFERENCES Aviones (idAvion),
  CONSTRAINT idcopiloto
    FOREIGN KEY (idPiloto)
    REFERENCES Pilotos (idPiloto));

INSERT INTO usuarios(
	idusuario, nombre, usuario, contrasena, rol)
	VALUES (1,'administracion','admin','123','Administrador');
