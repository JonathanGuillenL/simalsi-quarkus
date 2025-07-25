INSERT INTO TipoCliente(id, descripcion) values (1, 'Cliente espontaneo');
INSERT INTO TipoCliente(id, descripcion) values (2, 'Medico afiliado');
INSERT INTO TipoCliente(id, descripcion) values (3, 'Clinica afiliada');

INSERT INTO Cliente(id, username, email, telefono, tipocliente_id) values (100, 'bob', 'bob@gmail.com', '22523075', 1);
INSERT INTO ClienteEspontaneo(id, nombres, apellidos, cedula)
values (100, 'Bob', 'Martinez', '001-010101-0001A');

INSERT INTO Cargo(id, nombre, requiereCodigoSanitario)
values (100, 'Recepcionista', false);
INSERT INTO Colaborador(id, nombres, apellidos, numeroIdentificacion, telefono, username, email, cargo_id)
values (100, 'Jon', 'Gui', '001-020202-0002B', '80808080', 'jguillen', 'jguillen@local.dev', 100);

INSERT INTO Descuento(id, descripcion, porcentaje, fechaInicio, fechaFin, anual, automatico)
values (100, 'Dia de las madres', 10.0, '2025-05-30', '2025-07-29', true, true);

INSERT INTO DetalleFactura(id, precio, facturado) values (100, 100.0, false);
INSERT INTO SolicitudCGO(id, observaciones, recepcionista_id, cliente_id)
values (100, 'PRUEBA', 100, 100);

INSERT INTO DetalleFactura(id, precio, facturado) values (101, 100.0, false);
INSERT INTO SolicitudCGO(id, observaciones, recepcionista_id, cliente_id)
values (101, 'PRUEBA', 100, 100);
