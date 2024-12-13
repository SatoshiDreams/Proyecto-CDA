create database pruebacda;
use pruebacda;

CREATE TABLE rol (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  rol VARCHAR(50) NOT NULL
);

CREATE TABLE clientes (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  credencial VARCHAR(50) NOT NULL,
  nombre VARCHAR(100) NOT NULL,
  telefono VARCHAR(15) NOT NULL
);

CREATE TABLE empleados (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  documento VARCHAR(50) NOT NULL,
  nombre VARCHAR(100) NOT NULL,
  contraseña VARCHAR(100),
  fk_rol_id VARCHAR(36) NOT NULL,
  esta_activo BOOLEAN,
  FOREIGN KEY (fk_rol_id) REFERENCES rol(id)
);

CREATE TABLE vehiculos (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  tipo VARCHAR(50),
  matricula VARCHAR(50) NOT NULL,
  fk_cliente_id VARCHAR(36) NOT NULL,
  FOREIGN KEY (fk_cliente_id) REFERENCES clientes(id)
);

CREATE TABLE diagnosticos (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  fecha DATE NOT NULL,
  resultado VARCHAR(100) NOT NULL,
  observaciones TEXT,
  duracion TIME,
  fk_vehiculo_id VARCHAR(36) NOT NULL,
  fk_empleado_id VARCHAR(36) NOT NULL,
  FOREIGN KEY (fk_vehiculo_id) REFERENCES vehiculos(id),
  FOREIGN KEY (fk_empleado_id) REFERENCES empleados(id)
);

CREATE TABLE rol_respaldo (
  id VARCHAR(36) NOT NULL,
  rol VARCHAR(50) NOT NULL,
  fecha_respaldo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id, fecha_respaldo)
);

CREATE TABLE clientes_respaldo (
  id VARCHAR(36) NOT NULL,
  credencial VARCHAR(50) NOT NULL,
  nombre VARCHAR(100) NOT NULL,
  telefono VARCHAR(15) NOT NULL,
  fecha_respaldo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id, fecha_respaldo)
);

CREATE TABLE empleados_respaldo (
  id VARCHAR(36) NOT NULL,
  documento VARCHAR(50) NOT NULL,
  nombre VARCHAR(100) NOT NULL,
  contraseña VARCHAR(100),
  fk_rol_id VARCHAR(36) NOT NULL,
  esta_activo BOOLEAN,
  fecha_respaldo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id, fecha_respaldo)
);

CREATE TABLE vehiculos_respaldo (
  id VARCHAR(36) NOT NULL,
  tipo VARCHAR(50),
  matricula VARCHAR(50) NOT NULL,
  fk_cliente_id VARCHAR(36) NOT NULL,
  fecha_respaldo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id, fecha_respaldo)
);

CREATE TABLE diagnosticos_respaldo (
  id VARCHAR(36) NOT NULL,
  fecha DATE NOT NULL,
  resultado VARCHAR(100) NOT NULL,
  observaciones TEXT,
  duracion TIME,
  fk_vehiculo_id VARCHAR(36) NOT NULL,
  fk_empleado_id VARCHAR(36) NOT NULL,
  fecha_respaldo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id, fecha_respaldo)
);

DELIMITER //

CREATE PROCEDURE ObtenerClientePorCredencial(IN credencial_cliente VARCHAR(255))
BEGIN
    SELECT *
    FROM clientes
    WHERE credencial = credencial_cliente;
END //


DELIMITER //

CREATE PROCEDURE ObtenerDiagnosticosPorTipoDeVehiculo(IN tipo_entrada VARCHAR(255))
BEGIN
    SELECT 
        d.*  
    FROM 
        diagnosticos d 
    INNER JOIN 
        vehiculos v ON d.fk_vehiculo_id = v.id 
    WHERE 
        v.tipo = tipo_entrada;  
END //


DELIMITER //

CREATE PROCEDURE ObtenerDiagnosticosPorAoYMes(IN ao_entrada INT, IN mes_entrada INT)
BEGIN
    SELECT 
        d.*  
    FROM 
        diagnosticos d 
    INNER JOIN 
        vehiculos v ON d.fk_vehiculo_id = v.id 
    WHERE 
        YEAR(d.fecha) = ao_entrada  
        AND MONTH(d.fecha) = mes_entrada;  
END //


DELIMITER //

CREATE PROCEDURE ObtenerDiagnosticosPorAo(IN ao_entrada INT)
BEGIN
    SELECT 
        d.*  
    FROM 
        diagnosticos d 
    INNER JOIN 
        vehiculos v ON d.fk_vehiculo_id = v.id 
    INNER JOIN 
        clientes c ON v.fk_cliente_id = c.id 
    WHERE 
        YEAR(d.fecha) = ao_entrada;
END //


DELIMITER //

CREATE PROCEDURE ObtenerPromedioTiempoDiagnosticoPorTipoDeVehiculo()
BEGIN
    SELECT 
        v.tipo, 
        AVG(TIME_TO_SEC(d.duracion)) / 60 AS tiempo_promedio_minutos 
    FROM 
        diagnosticos d 
    INNER JOIN 
        vehiculos v ON d.fk_vehiculo_id = v.id 
    GROUP BY 
        v.tipo;
END //


DELIMITER //

CREATE PROCEDURE ObtenerEmpleadoPorDocumento(IN documento_entrada VARCHAR(255))
BEGIN
    SELECT 
        e.*  
    FROM 
        empleados e 
    WHERE 
        e.documento = documento_entrada;  
END //

DELIMITER //

CREATE PROCEDURE ObtenerTodosLosEmpleados()
BEGIN
    SELECT 
        e.*  
    FROM 
        empleados e;
END //


DELIMITER //

CREATE PROCEDURE EliminarEmpleadoPorDocumento(IN documento_entrada VARCHAR(255))
BEGIN
    DELETE FROM 
        empleados e 
    WHERE 
        e.documento = documento_entrada;  
END //



DELIMITER //

CREATE PROCEDURE CambiarEstadoActivoEmpleado(IN documento_entrada VARCHAR(255))
BEGIN
    UPDATE 
        empleados e 
    SET 
        e.esta_activo = CASE 
            WHEN e.esta_activo = TRUE THEN FALSE 
            ELSE TRUE 
        END 
    WHERE 
        e.documento = documento_entrada;  
END //


DELIMITER //

CREATE PROCEDURE ObtenerRolPorNombre(IN rol_entrada VARCHAR(255))
BEGIN
    SELECT 
        r.*  
    FROM 
        rol r 
    WHERE 
        r.rol = rol_entrada;  
END //


DELIMITER //

CREATE PROCEDURE ObtenerTodosLosRoles()
BEGIN
    SELECT 
        r.*  
    FROM 
        rol r;  
END //

DELIMITER //

CREATE PROCEDURE ExisteRolNoBloqueado(
    IN nombre_rol VARCHAR(50)
)
BEGIN
    
    SELECT * FROM empleados e INNER JOIN rol r ON e.fk_rol_id = r.id
    WHERE r.rol = nombre_rol AND e.esta_activo = true;

END //

DELIMITER //

CREATE PROCEDURE ObtenerVehiculoPorMatricula(IN matricula_entrada VARCHAR(255))
BEGIN
    SELECT 
        v.*  
    FROM 
        vehiculos v 
    WHERE 
        v.matricula = matricula_entrada; 
END //

DELIMITER ;





DELIMITER //


CREATE TRIGGER clientes_respaldo_antes_de_insertar
BEFORE INSERT ON clientes
FOR EACH ROW
BEGIN
  INSERT INTO clientes_respaldo (id, credencial, nombre, telefono, fecha_respaldo)
  VALUES (NEW.id, NEW.credencial, NEW.nombre, NEW.telefono, NOW());
END //

CREATE TRIGGER clientes_respaldo_antes_de_actualizar
BEFORE UPDATE ON clientes
FOR EACH ROW
BEGIN
  INSERT INTO clientes_respaldo (id, credencial, nombre, telefono, fecha_respaldo)
  VALUES (OLD.id, OLD.credencial, OLD.nombre, OLD.telefono, NOW());
END //

CREATE TRIGGER clientes_respaldo_antes_de_eliminar
BEFORE DELETE ON clientes
FOR EACH ROW
BEGIN
  INSERT INTO clientes_respaldo (id, credencial, nombre, telefono, fecha_respaldo)
  VALUES (OLD.id, OLD.credencial, OLD.nombre, OLD.telefono, NOW());
END //


CREATE TRIGGER empleados_respaldo_antes_de_insertar
BEFORE INSERT ON empleados
FOR EACH ROW
BEGIN
  INSERT INTO empleados_respaldo (id, documento, nombre, contraseña, fk_rol_id, esta_activo, fecha_respaldo)
  VALUES (NEW.id, NEW.documento, NEW.nombre, NEW.contraseña, NEW.fk_rol_id, NEW.esta_activo, NOW());
END //

CREATE TRIGGER empleados_respaldo_antes_de_actualizar
BEFORE UPDATE ON empleados
FOR EACH ROW
BEGIN
  INSERT INTO empleados_respaldo (id, documento, nombre, contraseña, fk_rol_id, esta_activo, fecha_respaldo)
  VALUES (OLD.id, OLD.documento, OLD.nombre, OLD.contraseña, OLD.fk_rol_id, OLD.esta_activo, NOW());
END //

CREATE TRIGGER empleados_respaldo_antes_de_eliminar
BEFORE DELETE ON empleados
FOR EACH ROW
BEGIN
  INSERT INTO empleados_respaldo (id, documento, nombre, contraseña, fk_rol_id, esta_activo, fecha_respaldo)
  VALUES (OLD.id, OLD.documento, OLD.nombre, OLD.contraseña, OLD.fk_rol_id, OLD.esta_activo, NOW());
END //


CREATE TRIGGER vehiculos_respaldo_antes_de_insertar
BEFORE INSERT ON vehiculos
FOR EACH ROW
BEGIN
  INSERT INTO vehiculos_respaldo (id, tipo, matricula, fk_cliente_id, fecha_respaldo)
  VALUES (NEW.id, NEW.tipo, NEW.matricula, NEW.fk_cliente_id, NOW());
END //

CREATE TRIGGER vehiculos_respaldo_antes_de_actualizar
BEFORE UPDATE ON vehiculos
FOR EACH ROW
BEGIN
  INSERT INTO vehiculos_respaldo (id, tipo, matricula, fk_cliente_id, fecha_respaldo)
  VALUES (OLD.id, OLD.tipo, OLD.matricula, OLD.fk_cliente_id, NOW());
END //

CREATE TRIGGER vehiculos_respaldo_antes_de_eliminar
BEFORE DELETE ON vehiculos
FOR EACH ROW
BEGIN
  INSERT INTO vehiculos_respaldo (id, tipo, matricula, fk_cliente_id, fecha_respaldo)
  VALUES (OLD.id, OLD.tipo, OLD.matricula, OLD.fk_cliente_id, NOW());
END //


CREATE TRIGGER diagnosticos_respaldo_antes_de_insertar
BEFORE INSERT ON diagnosticos
FOR EACH ROW
BEGIN
  INSERT INTO diagnosticos_respaldo (id, fecha, resultado, observaciones, duracion, fk_vehiculo_id, fk_empleado_id, fecha_respaldo)
  VALUES (NEW.id, NEW.fecha, NEW.resultado, NEW.observaciones, NEW.duracion, NEW.fk_vehiculo_id, NEW.fk_empleado_id, NOW());
END //

CREATE TRIGGER diagnosticos_respaldo_antes_de_actualizar
BEFORE UPDATE ON diagnosticos
FOR EACH ROW
BEGIN
  INSERT INTO diagnosticos_respaldo (id, fecha, resultado, observaciones, duracion, fk_vehiculo_id, fk_empleado_id, fecha_respaldo)
  VALUES (OLD.id, OLD.fecha, OLD.resultado, OLD.observaciones, OLD.duracion, OLD.fk_vehiculo_id, OLD.fk_empleado_id, NOW());
END //

CREATE TRIGGER diagnosticos_respaldo_antes_de_eliminar
BEFORE DELETE ON diagnosticos
FOR EACH ROW
BEGIN
  INSERT INTO diagnosticos_respaldo (id, fecha, resultado, observaciones, duracion, fk_vehiculo_id, fk_empleado_id, fecha_respaldo)
  VALUES (OLD.id, OLD.fecha, OLD.resultado, OLD.observaciones, OLD.duracion, OLD.fk_vehiculo_id, OLD.fk_empleado_id, NOW());
END //

CREATE TRIGGER rol_respaldo_antes_de_insertar
BEFORE INSERT ON rol
FOR EACH ROW
BEGIN
  INSERT INTO rol_respaldo (id, rol, fecha_respaldo)
  VALUES (NEW.id, NEW.rol, NOW());
END //

CREATE TRIGGER rol_respaldo_antes_de_actualizar
BEFORE UPDATE ON rol
FOR EACH ROW
BEGIN
  INSERT INTO rol_respaldo (id, rol, fecha_respaldo)
  VALUES (OLD.id, OLD.rol, NOW());
END //

CREATE TRIGGER rol_respaldo_antes_de_eliminar
BEFORE DELETE ON rol
FOR EACH ROW
BEGIN
  INSERT INTO rol_respaldo (id, rol, fecha_respaldo)
  VALUES (OLD.id, OLD.rol, NOW());
END //

DELIMITER ;

