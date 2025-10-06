INSERT INTO RegionAnatomica(id, descripcion) values (100, 'Región prueba');
INSERT INTO ProcedimientoQuirurgico(id, descripcion, region_anatomica_id) values (100, 'Procedimiento prueba', 100);
INSERT INTO ServicioLaboratorio(id, descripcion, precio, procedimiento_id) values (100, 'Servicio prueba', 125, 100);

INSERT INTO Moneda(id, descripcion, tipoCambio, signoMonetario) values (100, 'Córdoba', 1.0, 'C$');
INSERT INTO MetodoPago(id, descripcion) values (100, 'Contado');

-- Departamento Managua
INSERT INTO Departamento(id, descripcion) VALUES (100, 'Managua');

-- Municipios de Managua
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1001, 'Ciudad Sandino', 100);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1002, 'El Crucero', 100);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (100, 'Managua', 100);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1004, 'Mateare', 100);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1005, 'San Francisco Libre', 100);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1006, 'San Rafael del Sur', 100);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1007, 'Ticuantepe', 100);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1008, 'Tipitapa', 100);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1009, 'Villa El Carmen', 100);

-- Departamento Masaya
INSERT INTO Departamento(id, descripcion) VALUES (101, 'Masaya');

-- Municipios de Masaya
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1011, 'Catarina', 101);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1012, 'La Concepción', 101);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1013, 'Masatepe', 101);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1014, 'Masaya', 101);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1015, 'Nandasmo', 101);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1016, 'Nindirí', 101);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1017, 'Niquinohomo', 101);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1018, 'San Juan de Oriente', 101);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1019, 'Tisma', 101);

-- Departamento Carazo
INSERT INTO Departamento(id, descripcion) VALUES (102, 'Carazo');

-- Municipios de Carazo
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1021, 'Diriamba', 102);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1022, 'Dolores', 102);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1023, 'El Rosario', 102);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1024, 'Jinotepe', 102);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1025, 'La Conquista', 102);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1026, 'La Paz de Oriente', 102);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1027, 'San Marcos', 102);
INSERT INTO Municipio(id, descripcion, departamento_id) VALUES (1028, 'Santa Teresa', 102);

INSERT INTO Caja(id, numeroFilas, numeroColumnas) values (100, 10, 5);
INSERT INTO Caja(id, numeroFilas, numeroColumnas) values (101, 5, 5);
INSERT INTO Caja(id, numeroFilas, numeroColumnas) values (102, 5, 5);
INSERT INTO Caja(id, numeroFilas, numeroColumnas) values (103, 5, 5);

INSERT INTO Persona(id, nombre, telefono, direccion) values (100, 'Jonathan Alexander', '22523075', 'Distrito A');
INSERT INTO PersonaNatural(id , apellido, numeroIdentificacion)
values (100, 'Guillen Lainez', '001-121212-1000A');

INSERT INTO Cliente(id, username, email, persona_id, tipoCliente) values (100, 'jguillenl', 'jguillenl@gmail.com', 100, 1);

INSERT INTO Paciente(id, nacimiento, sexo, persona_id) values (100, '2002-02-02', 0, 100);

INSERT INTO Persona(id, nombre, telefono, direccion) values (101, 'Alberto Antonio', '22523075', 'Distrito B');
INSERT INTO PersonaNatural(id , apellido, numeroIdentificacion)
values (101, 'Emes Barahona', '001-121212-1000B');

INSERT INTO MedicoTratante(id, codigoSanitario, persona_id) values (100, '10124', 101);

INSERT INTO Cargo(id, nombre, codigo, requiereCodigoSanitario)
values (100, 'Recepcionista', 'RECP', false);
INSERT INTO Colaborador(id, nombres, apellidos, numeroIdentificacion, telefono, username, email, cargo_id)
values (100, 'Mariela', 'López García', '401-020290-0120X', '84111124', 'recepcionista', 'recepcionista@local.dev', 100);

INSERT INTO Cargo(id, nombre, codigo, requiereCodigoSanitario)
values (101, 'Patólogo', 'PATO', true);
INSERT INTO Colaborador(id, nombres, apellidos, numeroIdentificacion, codigoSanitario, telefono, username, email, cargo_id)
values (101, 'Nubia Leticia', 'López García', '401-020267-0001X', '13654', '57408008', 'patologo', 'patologo@local.dev', 101);

INSERT INTO Cargo(id, nombre, codigo, requiereCodigoSanitario)
values (102, 'Histotecnólogo', 'HIST', true);
INSERT INTO Colaborador(id, nombres, apellidos, numeroIdentificacion, codigoSanitario, telefono, username, email, cargo_id)
values (102, 'Mario Alberto', 'Sánchez Torrez', '005-201265-0002X', '13123', '58298384', 'histotecnologo', 'histotecnologo@local.dev', 102);

INSERT INTO Cargo(id, nombre, codigo, requiereCodigoSanitario)
values (103, 'ADMIN', 'ADMIN', true);
INSERT INTO Colaborador(id, nombres, apellidos, numeroIdentificacion, codigoSanitario, telefono, username, email, cargo_id)
values (103, 'Leticia', 'López García', '401-020290-0121X', '65781', '84541246', 'admin', 'admin@local.dev', 103);

INSERT INTO Descuento(id, descripcion, porcentaje, fechaInicio, fechaFin, anual, automatico)
values (100, 'Dia de las madres', 10.0, '2025-01-01', '2025-12-31', true, true);

-- Paciente 1
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (200, 'Carlos Alberto', '22521001', 'Distrito I');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (200, 'García López', '001-150185-2000A');
INSERT INTO Paciente(id, nacimiento, sexo, persona_id)
VALUES (200, '1985-01-15', 0, 200);

-- Paciente 2
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (201, 'María Fernanda', '22521002', 'Distrito II');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (201, 'Hernández Cruz', '001-220390-2001B');
INSERT INTO Paciente(id, nacimiento, sexo, persona_id)
VALUES (201, '1990-03-22', 1, 201);

-- Paciente 3
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (202, 'José Antonio', '22521003', 'Distrito III');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (202, 'Martínez Rivera', '001-090778-2002C');
INSERT INTO Paciente(id, nacimiento, sexo, persona_id)
VALUES (202, '1978-07-09', 0, 202);

-- Paciente 4
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (203, 'Ana Sofía', '22521004', 'Distrito IV');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (203, 'López Gutiérrez', '001-301188-2003D');
INSERT INTO Paciente(id, nacimiento, sexo, persona_id)
VALUES (203, '1988-11-30', 1, 203);

-- Paciente 5
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (204, 'Luis Enrique', '22521005', 'Distrito V');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (204, 'Ramírez Torres', '001-140595-2004E');
INSERT INTO Paciente(id, nacimiento, sexo, persona_id)
VALUES (204, '1995-05-14', 0, 204);

-- Paciente 6
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (205, 'Marta Isabel', '22521006', 'Distrito VI');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (205, 'Cruz Morales', '001-170282-2005F');
INSERT INTO Paciente(id, nacimiento, sexo, persona_id)
VALUES (205, '1982-02-17', 1, 205);

-- Paciente 7
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (206, 'Pedro Alejandro', '22521007', 'Distrito VII');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (206, 'Gómez Castillo', '001-250693-2006G');
INSERT INTO Paciente(id, nacimiento, sexo, persona_id)
VALUES (206, '1993-06-25', 0, 206);

-- Paciente 8
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (207, 'Lucía Beatriz', '22521008', 'Distrito VIII');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (207, 'Vargas Aguilar', '001-190987-2007H');
INSERT INTO Paciente(id, nacimiento, sexo, persona_id)
VALUES (207, '1987-09-19', 1, 207);

-- Paciente 9
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (208, 'Jorge Luis', '22521009', 'Distrito IX');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (208, 'Mendoza Rivas', '001-080475-2008J');
INSERT INTO Paciente(id, nacimiento, sexo, persona_id)
VALUES (208, '1975-04-08', 0, 208);

-- Paciente 10
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (209, 'Elena Patricia', '22521010', 'Distrito X');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (209, 'Silva Fonseca', '001-011299-2009K');
INSERT INTO Paciente(id, nacimiento, sexo, persona_id)
VALUES (209, '1999-12-01', 1, 209);


-- Medico 1
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (301, 'Francisco Javier', '22524001', 'Distrito XI');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (301, 'Lozano Mejía', '002-150176-3001A');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (300, '20101', 301);

-- Medico 2
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (302, 'Gabriela Elena', '22524002', 'Distrito XII');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (302, 'Bermúdez Salgado', '002-220482-3002B');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (301, '20102', 302);

-- Medico 3
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (303, 'Rodrigo Daniel', '22524003', 'Distrito XIII');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (303, 'Palacios Centeno', '002-090775-3003C');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (302, '20103', 303);

-- Medico 4
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (304, 'Verónica Alejandra', '22524004', 'Distrito XIV');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (304, 'Zamora Talavera', '002-301186-3004D');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (303, '20104', 304);

-- Medico 5
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (305, 'Ricardo Esteban', '22524005', 'Distrito XV');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (305, 'Sequeira Molina', '002-140596-3005E');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (304, '20105', 305);

-- Medico 6
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (306, 'Claudia Marcela', '22524006', 'Distrito XVI');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (306, 'Montenegro Solís', '002-170283-3006F');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (305, '20106', 306);

-- Medico 7
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (307, 'Fernando José', '22524007', 'Distrito XVII');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (307, 'Altamirano Pineda', '002-250692-3007G');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (306, '20107', 307);

-- Medico 8
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (308, 'Patricia del Carmen', '22524008', 'Distrito XVIII');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (308, 'Avilés Rocha', '002-190985-3008H');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (307, '20108', 308);

-- Medico 9
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (309, 'Esteban Mauricio', '22524009', 'Distrito XIX');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (309, 'Aragón Ruiz', '002-080476-3009J');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (308, '20109', 309);

-- Medico 10
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (310, 'Rosa Amalia', '22524010', 'Distrito XX');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (310, 'Fonseca Blandón', '002-011298-3010K');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (309, '20110', 310);

-- Cliente Jurídico 1
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (501, 'Clínica Vida Sana', '22531001', 'Barrio Monseñor Lezcano');
INSERT INTO PersonaJuridica(id, RUC, razonSocial, municipio_id)
VALUES (501, 'J031-010125-0001A', 'Clínica Vida Sana S.A.', 100);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (500, 3, 501, 'vidasana001@example.com', 'vidasana4821');

-- Cliente Jurídico 2
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (502, 'Hospital San Rafael', '22531002', 'Colonia Centroamérica');
INSERT INTO PersonaJuridica(id, RUC, razonSocial, municipio_id)
VALUES (502, 'J031-020225-0002B', 'Hospital San Rafael S.A.', 100);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (501, 3, 502, 'sanrafael002@example.com', 'sanrafael9365');

-- Cliente Jurídico 3
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (503, 'Laboratorios BioSalud', '22531003', 'Carretera Masaya, km 6');
INSERT INTO PersonaJuridica(id, RUC, razonSocial, municipio_id)
VALUES (503, 'J031-030325-0003C', 'Laboratorios BioSalud S.A.', 100);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (502, 3, 503, 'biosalud003@example.com', 'biosalud7230');

-- Cliente Jurídico 4
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (504, 'Centro Médico Los Ángeles', '22531004', 'Altamira, Managua');
INSERT INTO PersonaJuridica(id, RUC, razonSocial, municipio_id)
VALUES (504, 'J031-040425-0004D', 'Centro Médico Los Ángeles S.A.', 100);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (503, 3, 504, 'losangeles004@example.com', 'losangeles5172');

-- Cliente Jurídico 5
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (505, 'Hospital Vida Plena', '22531005', 'Distrito II, Managua');
INSERT INTO PersonaJuridica(id, RUC, razonSocial, municipio_id)
VALUES (505, 'J031-050525-0005E', 'Hospital Vida Plena S.A.', 100);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (504, 3, 505, 'vidaplena005@example.com', 'vidaplena2089');

-- Cliente Jurídico 6
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (506, 'Clínica Especializada San José', '22531006', 'Carretera Norte, km 8');
INSERT INTO PersonaJuridica(id, RUC, razonSocial, municipio_id)
VALUES (506, 'J031-060625-0006F', 'Clínica Especializada San José S.A.', 100);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (505, 3, 506, 'sanjose006@example.com', 'sanjose6734');

-- Cliente Jurídico 7
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (507, 'Hospital Metropolitano Salud Total', '22531007', 'Distrito IV, Managua');
INSERT INTO PersonaJuridica(id, RUC, razonSocial, municipio_id)
VALUES (507, 'J031-070725-0007G', 'Hospital Metropolitano Salud Total S.A.', 100);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (506, 3, 507, 'saludtotal007@example.com', 'saludtotal9542');

-- Cliente Jurídico 8
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (508, 'Clínica Integral Esperanza', '22531008', 'Jinotepe, Carazo');
INSERT INTO PersonaJuridica(id, RUC, razonSocial, municipio_id)
VALUES (508, 'J031-080825-0008H', 'Clínica Integral Esperanza S.A.', 100);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (507, 3, 508, 'esperanza008@example.com', 'esperanza4210');

-- Cliente Jurídico 9
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (509, 'Centro de Diagnóstico Avanzado', '22531009', 'León, Nicaragua');
INSERT INTO PersonaJuridica(id, RUC, razonSocial, municipio_id)
VALUES (509, 'J031-090925-0009J', 'Centro de Diagnóstico Avanzado S.A.', 100);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (508, 3, 509, 'diagnostico009@example.com', 'diagnostico8361');

-- Cliente Jurídico 10
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (510, 'Hospital La Merced', '22531010', 'Granada, Nicaragua');
INSERT INTO PersonaJuridica(id, RUC, razonSocial, municipio_id)
VALUES (510, 'J031-101025-0010K', 'Hospital La Merced S.A.', 100);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (509, 3, 510, 'lamerced010@example.com', 'lamerced1927');


-- Cliente / Medico 1
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (601, 'Alfonso Manuel', '22532001', 'Colonia Centro');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (601, 'Gómez Rivas', '001-150185-6001A');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (600, 'MT10101', 601);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (600, 2, 601, 'alfonsomanuel001@example.com', 'alfonsomanuel4821');

-- Cliente / Medico 2
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (602, 'Beatriz Elena', '22532002', 'Barrio San José');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (602, 'Ramírez Cruz', '001-220390-6002B');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (601, 'MT10102', 602);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (601, 2, 602, 'beatrizelena002@example.com', 'beatrizelena9365');

-- Cliente / Medico 3
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (603, 'Carlos Alberto', '22532003', 'Colonia Nuevo Amanecer');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (603, 'Martínez López', '001-090778-6003C');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (602, 'MT10103', 603);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (602, 2, 603, 'carlosalberto003@example.com', 'carlosalberto1478');

-- Cliente / Medico 4
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (604, 'Diana Sofía', '22532004', 'Colonia Santa Ana');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (604, 'López Gutiérrez', '001-301188-6004D');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (603, 'MT10104', 604);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (603, 2, 604, 'dianasofia004@example.com', 'dianasofia5172');

-- Cliente / Medico 5
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (605, 'Eduardo Javier', '22532005', 'Barrio Monseñor Lezcano');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (605, 'Ramírez Torres', '001-140595-6005E');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (604, 'MT10105', 605);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (604, 2, 605, 'eduardojavier005@example.com', 'eduardojavier2089');

-- Cliente / Medico 6
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (606, 'Fernanda Isabel', '22532006', 'Colonia Altamira');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (606, 'Cruz Morales', '001-170282-6006F');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (605, 'MT10106', 606);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (605, 2, 606, 'fernandaisabel006@example.com', 'fernandaisabel6734');

-- Cliente / Medico 7
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (607, 'Gustavo Daniel', '22532007', 'Barrio El Dorado');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (607, 'Gómez Castillo', '001-250693-6007G');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (606, 'MT10107', 607);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (606, 2, 607, 'gustavodaniel007@example.com', 'gustavodaniel9542');

-- Cliente / Medico 8
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (608, 'Helena Beatriz', '22532008', 'Colonia La Merced');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (608, 'Vargas Aguilar', '001-190987-6008H');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (607, 'MT10108', 608);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (607, 2, 608, 'helenabeatriz008@example.com', 'helenabeatriz4210');

-- Cliente / Medico 9
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (609, 'Ignacio Luis', '22532009', 'Colonia Centroamérica');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (609, 'Mendoza Rivas', '001-080475-6009J');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (608, 'MT10109', 609);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (608, 2, 609, 'ignacioluis009@example.com', 'ignacioluis8361');

-- Cliente / Medico 10
INSERT INTO Persona(id, nombre, telefono, direccion)
VALUES (610, 'Juliana Patricia', '22532010', 'Colonia La Esperanza');
INSERT INTO PersonaNatural(id, apellido, numeroIdentificacion)
VALUES (610, 'Silva Fonseca', '001-011299-6010K');
INSERT INTO MedicoTratante(id, codigoSanitario, persona_id)
VALUES (609, 'MT10110', 610);
INSERT INTO Cliente(id, tipoCliente, persona_id, email, username)
VALUES (609, 2, 610, 'julianapatricia010@example.com', 'julianapatricia1927');

INSERT INTO regionanatomica (id, descripcion) VALUES
  (200, 'Antro gástrico'),
  (201, 'Colon'),
  (202, 'Recto'),
  (203, 'Esófago'),
  (204, 'Duodeno'),
  (205, 'Hígado'),
  (206, 'Vesícula biliar'),
  (207, 'Pulmón'),
  (208, 'Mama'),
  (209, 'Cuello uterino');

-- 4) procedimientoquirurgico
-- Antro gástrico
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Biopsia endoscópica del antro' FROM regionanatomica WHERE descripcion='Antro gástrico';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Resección endoscópica de lesión gástrica' FROM regionanatomica WHERE descripcion='Antro gástrico';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Gastrektomía parcial (antro)' FROM regionanatomica WHERE descripcion='Antro gástrico';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Polipectomía gástrica' FROM regionanatomica WHERE descripcion='Antro gástrico';

-- Colon
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Biopsia colonoscópica' FROM regionanatomica WHERE descripcion='Colon';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Polipectomía colónica' FROM regionanatomica WHERE descripcion='Colon';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Hemicolectomía' FROM regionanatomica WHERE descripcion='Colon';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Mucosectomía endoscópica (EMR) de colon' FROM regionanatomica WHERE descripcion='Colon';

-- Recto
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Biopsia rectal' FROM regionanatomica WHERE descripcion='Recto';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Resección transanal (TAMIS/TEMS)' FROM regionanatomica WHERE descripcion='Recto';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Polipectomía rectal' FROM regionanatomica WHERE descripcion='Recto';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Resección anterior baja' FROM regionanatomica WHERE descripcion='Recto';

-- Esófago
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Biopsia endoscópica esofágica' FROM regionanatomica WHERE descripcion='Esófago';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Mucosectomía endoscópica (EMR) esofágica' FROM regionanatomica WHERE descripcion='Esófago';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Esofagectomía parcial' FROM regionanatomica WHERE descripcion='Esófago';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Radiofrecuencia/ablación con biopsia de control' FROM regionanatomica WHERE descripcion='Esófago';

-- Duodeno
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Biopsia endoscópica duodenal' FROM regionanatomica WHERE descripcion='Duodeno';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Polipectomía duodenal' FROM regionanatomica WHERE descripcion='Duodeno';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Ampulectomía endoscópica' FROM regionanatomica WHERE descripcion='Duodeno';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Resección segmentaria duodenal' FROM regionanatomica WHERE descripcion='Duodeno';

-- Hígado
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Biopsia hepática por aguja' FROM regionanatomica WHERE descripcion='Hígado';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Lobectomía hepática' FROM regionanatomica WHERE descripcion='Hígado';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Resección de lesión focal hepática' FROM regionanatomica WHERE descripcion='Hígado';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Biopsia transyugular (TIPS) con muestra' FROM regionanatomica WHERE descripcion='Hígado';

-- Vesícula biliar
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Colecistectomía' FROM regionanatomica WHERE descripcion='Vesícula biliar';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Biopsia de pared vesicular' FROM regionanatomica WHERE descripcion='Vesícula biliar';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Resección de pólipo vesicular' FROM regionanatomica WHERE descripcion='Vesícula biliar';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Colecistectomía parcial' FROM regionanatomica WHERE descripcion='Vesícula biliar';

-- Pulmón
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Biopsia pulmonar por aguja (transtorácica)' FROM regionanatomica WHERE descripcion='Pulmón';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Lobectomía pulmonar' FROM regionanatomica WHERE descripcion='Pulmón';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Segmentectomía pulmonar' FROM regionanatomica WHERE descripcion='Pulmón';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Biopsia endobronquial' FROM regionanatomica WHERE descripcion='Pulmón';

-- Mama
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Biopsia por core (tru-cut) de mama' FROM regionanatomica WHERE descripcion='Mama';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Cuadrantectomía / tumorectomía' FROM regionanatomica WHERE descripcion='Mama';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Mastectomía parcial' FROM regionanatomica WHERE descripcion='Mama';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Mastectomía total' FROM regionanatomica WHERE descripcion='Mama';

-- Cuello uterino
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Papanicolaou (toma citológica)' FROM regionanatomica WHERE descripcion='Cuello uterino';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Biopsia cervical dirigida' FROM regionanatomica WHERE descripcion='Cuello uterino';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Conización (LEEP/cono frío)' FROM regionanatomica WHERE descripcion='Cuello uterino';
INSERT INTO procedimientoquirurgico (region_anatomica_id, descripcion)
SELECT id, 'Histerectomía (pieza cervical incluida)' FROM regionanatomica WHERE descripcion='Cuello uterino';

-- ServicioLaboratorio 1
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (201, 45.00, 1, 'Biopsia endoscópica gástrica');

-- ServicioLaboratorio 2
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (202, 60.00, 2, 'Resección endoscópica de lesión gástrica');

-- ServicioLaboratorio 3
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (203, 75.00, 5, 'Polipectomía colónica avanzada');

-- ServicioLaboratorio 4
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (204, 50.00, 6, 'Hemicolectomía diagnóstica');

-- ServicioLaboratorio 5
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (205, 80.00, 9, 'Biopsia rectal');

-- ServicioLaboratorio 6
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (206, 95.00, 10, 'Resección transanal integral');

-- ServicioLaboratorio 7
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (207, 65.00, 13, 'Biopsia endoscópica esofágica');

-- ServicioLaboratorio 8
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (208, 85.00, 14, 'Mucosectomía esofágica');

-- ServicioLaboratorio 9
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (209, 120.00, 17, 'Biopsia hepática por aguja con informe rápido');

-- ServicioLaboratorio 10
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (210, 150.00, 18, 'Lobectomía hepática diagnóstica');

-- ServicioLaboratorio 11
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (211, 40.00, 21, 'Colecistectomía parcial');

-- ServicioLaboratorio 12
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (212, 55.00, 22, 'Biopsia de pared vesicular');

-- ServicioLaboratorio 13
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (213, 110.00, 25, 'Biopsia pulmonar transtorácica');

-- ServicioLaboratorio 14
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (2014, 130.00, 26, 'Lobectomía pulmonar diagnóstica');

-- ServicioLaboratorio 15
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (215, 50.00, 29, 'Biopsia por core de mama');

-- ServicioLaboratorio 16
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (216, 70.00, 30, 'Cuadrantectomía');

-- ServicioLaboratorio 19
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (219, 90.00, 35, 'Conización (LEEP)');

-- ServicioLaboratorio 20
INSERT INTO ServicioLaboratorio(id, precio, procedimiento_id, descripcion)
VALUES (220, 120.00, 36, 'Histerectomía (pieza cervical incluida)');

INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (100, '8140/3', 'Adenocarcinoma');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (101, '8480/3', 'Adenocarcinoma  mucinoso (coloide) (mas del 50% de mucina)');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (102, '8333/3', 'Adenocarcinoma fetal');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (103, '8560/3', 'Adenocarcinoma mixto');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (104, '8480/3', 'Adenocarcinoma mucinoso');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (105, '8010/3', 'Adenocarcinoma no especificado de otra manera, de alto grado');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (106, '8010/3', 'Adenocarcinoma no especificado de otra manera, de bajo grado');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (107, '8010/3', 'Adenocarcinoma no especificado de otra manera, grado intermedio');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (108, '8525/3', 'Adenocarcinoma polimorfo clásico, cribiforme (originado de glándula salival) grado intermedio');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (109, '8525/3', 'Adenocarcinoma polimorfo clásico, de alto grado');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (110, '8525/3', 'Adenocarcinoma polimorfo clásico, de bajo grado');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (111, '8525/3', 'Adenocarcinoma polimorfo cribiforme (adenocarcinoma cribiforme de origen salival) alto grado');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (112, '8525/3', 'Adenocarcinoma polimorfo cribiforme (adenocarcinoma cribiforme de origen salival) bajo grado');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (113, '8525/3', 'Adenocarcinoma polimorfo cribiforme (adenocarcinoma cribiforme de origen salival) intermedio grado');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (114, '8410/3', 'Adenocarcinoma sebáceo');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (115, '8140/3', 'Adenocarcinoma, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (116, '9911/3', 'AML (megacarioblástico) con t (1; 22) (p13; q13); RBM15-MKL1');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (117, '9120/3', 'Angiosarcoma');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (118, '8240/3', 'Carcinoide típico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (119, '8310/3', 'Carcinoma  de células escamosas de células claras');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (120, '8560/3', 'Carcinoma Adenoescamoso');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (121, '8200/3', 'Carcinoma Adenoideo Quístico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (122, '8094/3', 'Carcinoma basoescamoso');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (123, '8550/3', 'Carcinoma de células acinares');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (124, '8310/3', 'Carcinoma de células claras');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (125, '8310/3', 'Carcinoma de células claras, hialinizante');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (126, '8490/3', 'Carcinoma de células en anillo de sello (mas del 50% de células en anillo de sello)');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (127, '8070/3', 'Carcinoma de células escamosas');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (128, '8075/3', 'Carcinoma de células escamosas acantolitico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (129, '8083/3', 'Carcinoma de células escamosas basaloide');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (130, '8074/3', 'Carcinoma de células escamosas de celulas fusiformes');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (131, '8070/2', 'Carcinoma de células escamosas in situ');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (132, '8052/2', 'Carcinoma de células escamosas in situ (no hay carcinoma invasivo identificado)');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (133, '8083/3', 'Carcinoma de células escamosas invasivo basaloide');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (134, '8072/3', 'Carcinoma de células escamosas invasivo no queratinizante');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (135, '8071/3', 'Carcinoma de células escamosas invasivo queratinizante');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (136, '8052/3', 'Carcinoma de células escamosas papilar');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (137, '8050/3', 'Carcinoma de células escamosas papilar, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (138, '8051/3', 'Carcinoma de células escamosas verrucoso');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (139, '8090/3', 'Carcinoma de Células escamosas, basaloide');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (140, '8032/3', 'Carcinoma de células escamosas, de células fusiformes');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (141, '8120/3', 'Carcinoma de Células escamosas, escamotransicional');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (142, '8076/3', 'Carcinoma de Celulas escamosas, microinvasivo');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (143, '8041/3', 'Carcinoma de células pequeñas');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (144, '8246/3', 'Carcinoma de células grandes neuroendocrino');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (145, '8500/3', 'Carcinoma del conducto salivar');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (146, '8380/3', 'Carcinoma endometrioide');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (147, '8562/3', 'Carcinoma epitelial-mioepitelial');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (148, '8941/3', 'Carcinoma ex adenoma pleomórfico, invasivo');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (149, '8020/3', 'Carcinoma indiferenciado');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (150, '8082/3', 'Carcinoma in situ de células escamosas no queratinizante');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (151, '8070/2', 'Carcinoma in situ de células escamosas queratinizante');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (152, '8012/3', 'Carcinoma linfoepitelioma');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (153, '8010/3', 'Carcinoma microcítico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (154, '8020/3', 'Carcinoma indiferenciado, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (155, '8045/3', 'Carcinoma neuroendocrino de células grandes');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (156, '8041/3', 'Carcinoma neuroendocrino de células pequeñas');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (157, '8041/3', 'Carcinoma neuroendocrino indiferenciado');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (158, '8041/3', 'Carcinoma neuroendocrino, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (159, '8500/3', 'Carcinoma papilar');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (160, '8503/3', 'Carcinoma papilar intraductal');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (161, '8050/3', 'Carcinoma papilar de células escamosas');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (162, '8050/3', 'Carcinoma papilar escamoso');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (163, '8050/3', 'Carcinoma papilar, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (164, '8503/3', 'Carcinoma papilar sólido');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (165, '8310/3', 'Carcinoma renal de células claras');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (166, '8310/3', 'Carcinoma renal de células claras, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (167, '8050/2', 'Carcinoma verrucoso in situ');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (168, '8051/3', 'Carcinoma verrucoso, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (169, '8810/3', 'Fibrosarcoma');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (170, '9680/3', 'Linfoma no Hodgkin difuso, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (171, '9680/3', 'Linfoma no Hodgkin, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (172, '9731/3', 'Mieloma múltiple');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (173, '9732/3', 'Mieloma múltiple, tipo plasmocítico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (174, '9734/3', 'Plasmocitoma extramedular');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (175, '9731/3', 'Plasmocitoma múltiple');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (176, '9732/3', 'Plasmocitoma solitario óseo');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (177, '9590/3', 'Neoplasia maligna de tejido linfoide, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (178, '8720/3', 'Melanoma maligno');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (179, '8743/3', 'Melanoma desmoplásico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (180, '8770/3', 'Melanoma nodular');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (181, '8720/3', 'Melanoma superficial en extensión');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (182, '8720/3', 'Melanoma, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (183, '8800/3', 'Sarcoma, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (184, '8811/3', 'Sarcoma fibromixoide de bajo grado');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (185, '8850/3', 'Liposarcoma');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (186, '8851/3', 'Liposarcoma bien diferenciado');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (187, '8852/3', 'Liposarcoma mixoide');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (188, '8853/3', 'Liposarcoma de células redondas');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (189, '8854/3', 'Liposarcoma pleomórfico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (190, '8850/3', 'Liposarcoma, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (191, '8890/3', 'Leiomyosarcoma');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (192, '8936/3', 'Sarcoma del estroma endometrial de alto grado');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (193, '8930/3', 'Sarcoma del estroma endometrial de bajo grado');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (194, '8931/3', 'Sarcoma del estroma endometrial, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (195, '9040/3', 'Sarcoma sinovial, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (196, '9041/3', 'Sarcoma sinovial de células fusiformes');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (197, '9042/3', 'Sarcoma sinovial de células epiteliales');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (198, '9043/3', 'Sarcoma sinovial bifásico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (199, '8800/3', 'Sarcoma indiferenciado pleomórfico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (200, '8800/3', 'Sarcoma indiferenciado, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (201, '8806/3', 'Sarcoma epitelioide');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (202, '8804/3', 'Sarcoma alveolar de partes blandas');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (203, '8810/3', 'Fibrosarcoma infantil');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (204, '9120/3', 'Hemangiosarcoma');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (205, '9130/3', 'Hemangiopericitoma maligno');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (206, '9133/3', 'Tumor maligno fibroso histiocítico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (207, '9136/3', 'Angiomatoide fibroso histiocitoma');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (208, '9180/3', 'Osteosarcoma, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (209, '9181/3', 'Osteosarcoma central');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (210, '9182/3', 'Osteosarcoma parostal');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (211, '9183/3', 'Osteosarcoma periostal');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (212, '9184/3', 'Osteosarcoma de células pequeñas');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (213, '9185/3', 'Osteosarcoma telangiectásico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (214, '9186/3', 'Osteosarcoma de alto grado superficial');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (215, '9187/3', 'Osteosarcoma intraóseo bien diferenciado');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (216, '9180/3', 'Osteosarcoma, tipo convencional');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (217, '9191/3', 'Condrosarcoma, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (218, '9220/3', 'Condrosarcoma mixoide');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (219, '9231/3', 'Condrosarcoma mesenquimal');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (220, '9240/3', 'Condrosarcoma desdiferenciado');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (221, '9364/3', 'Sarcoma de Ewing');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (222, '9370/3', 'Neuroblastoma, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (223, '9470/3', 'Astrocitoma pilocítico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (224, '9400/3', 'Astrocitoma difuso');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (225, '9401/3', 'Astrocitoma anaplásico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (226, '9410/3', 'Astrocitoma gemistocítico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (227, '9420/3', 'Astrocitoma fibrilar');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (228, '9440/3', 'Glioblastoma multiforme');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (229, '9441/3', 'Glioblastoma con células gigantes');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (230, '9442/3', 'Glioblastoma epitelioide');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (231, '9450/3', 'Oligodendroglioma, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (232, '9451/3', 'Oligodendroglioma anaplásico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (233, '9460/3', 'Oligoastrocitoma');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (234, '9461/3', 'Oligoastrocitoma anaplásico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (235, '9471/3', 'Astrocitoma pilomixoide');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (236, '9490/3', 'Ganglioglioma, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (237, '9491/3', 'Ganglioglioma anaplásico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (238, '9500/3', 'Neurocitoma central');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (239, '9501/3', 'Neurocitoma extraventricular');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (240, '9505/3', 'Tumor glioneuronal desmoplásico infantil');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (241, '9506/3', 'Tumor glioneuronal papilar');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (242, '9509/3', 'Tumor glioneuronal rosetado difuso');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (243, '9520/3', 'Ependimoma, NOS');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (244, '9391/3', 'Ependimoma anaplásico');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (245, '9392/3', 'Ependimoma mixopapilar');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (246, '9393/3', 'Ependimoblastoma');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (247, '9394/3', 'Ependimoma subependimario de células claras');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (248, '9395/3', 'Ependimoma RELA fusionado');
INSERT INTO CodigoEnfermedades (id, codigo, descripcion) VALUES (249, '9396/3', 'Ependimoma anaplásico supratentorial');

INSERT INTO DetalleFactura(id, precio, facturado, servicioLaboratorio_id) values (100, 125.0, false, 100);
INSERT INTO SolicitudCGO(id, observaciones, estado, recepcionista, cliente_id, paciente_id, medicoTratante_id, fechaSolicitud, fechaTomaMuestra)
values (100, 'PRUEBA', 0, 'recepcionista', 100, 100, 100, '2025-07-22', '2025-07-21T06:20:00');

INSERT INTO DetalleFactura(id, precio, facturado, servicioLaboratorio_id) values (101, 125.0, false, 100);
INSERT INTO SolicitudCGO(id, observaciones, estado, recepcionista, cliente_id, paciente_id, fechaSolicitud, fechaTomaMuestra)
values (101, 'PRUEBA', 0, 'recepcionista', 100, 100, '2025-07-04T06:30:05', '2025-07-04T06:20:00');

INSERT INTO DetalleFactura(id, precio, facturado, servicioLaboratorio_id) values (102, 125.0, false, 100);
INSERT INTO SolicitudCGO(id, observaciones, estado, recepcionista, cliente_id, paciente_id, fechaSolicitud, fechaTomaMuestra)
values (102, 'PRUEBA', 0, 'recepcionista', 500, 200, '2025-06-04T06:30:05', '2025-07-04T06:20:00');

INSERT INTO DetalleFactura(id, precio, facturado, servicioLaboratorio_id) values (103, 125.0, false, 100);
INSERT INTO SolicitudCGO(id, observaciones, estado, recepcionista, cliente_id, paciente_id, fechaSolicitud, fechaTomaMuestra)
values (103, 'PRUEBA', 0, 'recepcionista', 500, 200, '2025-05-04T06:30:05', '2025-07-04T06:20:00');

INSERT INTO DetalleFactura(id, precio, facturado, servicioLaboratorio_id) values (104, 125.0, false, 100);
INSERT INTO SolicitudCGO(id, observaciones, estado, recepcionista, cliente_id, paciente_id, fechaSolicitud, fechaTomaMuestra)
values (104, 'PRUEBA', 0, 'recepcionista', 500, 200, '2025-05-04T06:30:05', '2025-07-04T06:20:00');

INSERT INTO DetalleFactura(id, precio, facturado, servicioLaboratorio_id) values (105, 125.0, false, 100);
INSERT INTO SolicitudCGO(id, observaciones, estado, recepcionista, cliente_id, paciente_id, fechaSolicitud, fechaTomaMuestra)
values (105, 'PRUEBA', 0, 'recepcionista', 500, 200, '2025-04-04T06:30:05', '2025-07-04T06:20:00');

INSERT INTO DetalleFactura(id, precio, facturado, servicioLaboratorio_id) values (106, 125.0, false, 100);
INSERT INTO SolicitudCGO(id, observaciones, estado, recepcionista, cliente_id, paciente_id, fechaSolicitud, fechaTomaMuestra)
values (106, 'PRUEBA', 0, 'recepcionista', 500, 200, '2025-03-04T06:30:05', '2025-07-04T06:20:00');
