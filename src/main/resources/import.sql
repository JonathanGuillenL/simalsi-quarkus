INSERT INTO RegionAnatomica(id, descripcion) values (100, 'Región prueba');
INSERT INTO ProcedimientoQuirurgico(id, descripcion, region_anatomica_id) values (100, 'Procedimiento prueba', 100);
INSERT INTO ServicioLaboratorio(id, descripcion, precio, procedimiento_id) values (100, 'Servicio prueba', 125, 100);

INSERT INTO Moneda(id, descripcion, tipoCambio, signoMonetario) values (100, 'Córdoba', 1.0, 'C$');
INSERT INTO MetodoPago(id, descripcion) values (100, 'Contado');

INSERT INTO Caja(id, numeroFilas, numeroColumnas) values (100, 15, 10);
INSERT INTO Caja(id, numeroFilas, numeroColumnas) values (101, 5, 5);
INSERT INTO Lamina(id, fila, columna, caja_id) values (100, 1, 1, 100);

INSERT INTO Persona(id, nombre, telefono, direccion) values (100, 'Jonathan Alexander', '22523075', 'Distrito A');
INSERT INTO PersonaNatural(id , apellido, numeroIdentificacion)
values (100, 'Guillen Lainez', '001-121212-1000A');

INSERT INTO Cliente(id, username, email, persona_id) values (100, 'bob', 'bob@gmail.com', 100);

INSERT INTO Paciente(id, nacimiento, persona_id) values (100, '2002-02-02', 100);

INSERT INTO Persona(id, nombre, telefono, direccion) values (101, 'Alberto Antonio', '22523075', 'Distrito B');
INSERT INTO PersonaNatural(id , apellido, numeroIdentificacion)
values (101, 'Emes Barahona', '001-121212-1000B');

INSERT INTO MedicoTratante(id, codigoSanitario, persona_id) values (100, '10124', 101);

INSERT INTO Cargo(id, nombre, requiereCodigoSanitario)
values (100, 'Recepcionista', false);
INSERT INTO Colaborador(id, nombres, apellidos, numeroIdentificacion, telefono, username, email, cargo_id)
values (100, 'Jon', 'Gui', '001-020202-0002B', '80808080', 'jguillen', 'jguillen@local.dev', 100);

INSERT INTO Descuento(id, descripcion, porcentaje, fechaInicio, fechaFin, anual, automatico)
values (100, 'Dia de las madres', 10.0, '2025-01-01', '2025-12-31', true, true);

INSERT INTO DetalleFactura(id, precio, facturado, servicioLaboratorio_id) values (100, 125.0, false, 100);
INSERT INTO SolicitudCGO(id, observaciones, estado, recepcionista_id, cliente_id, paciente_id)
values (100, 'PRUEBA', 0, 100, 100, 100);

INSERT INTO DetalleFactura(id, precio, facturado, servicioLaboratorio_id) values (101, 125.0, false, 100);
INSERT INTO SolicitudCGO(id, observaciones, estado, recepcionista_id, cliente_id, paciente_id)
values (101, 'PRUEBA', 0, 100, 100, 100);
