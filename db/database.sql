DROP DATABASE ordenaris_riskengine;
CREATE DATABASE ordenaris_riskengine;
USE ordenaris_riskengine;

CREATE TABLE empresa (
    id_empresa BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    rfc VARCHAR(20) UNIQUE NOT NULL,
    fecha_registro DATE NOT NULL
);

CREATE TABLE datos_contables (
    id_contable BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_empresa BIGINT NOT NULL,
    ventas_anuales DECIMAL(15,2) NOT NULL,
    deuda_activa DECIMAL(15,2) NOT NULL,
    flujo_efectivo DECIMAL(15,2) NOT NULL,
    fecha_actualizacion DATE NOT NULL,
    FOREIGN KEY (id_empresa) REFERENCES empresa(id_empresa)
);

CREATE TABLE verificacion_legal (
    id_verificacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_empresa BIGINT NOT NULL,
    tiene_demandas BOOLEAN DEFAULT FALSE,
    descripcion TEXT,
    fecha_verificacion DATE NOT NULL,
    FOREIGN KEY (id_empresa) REFERENCES empresa(id_empresa)
);


CREATE TABLE detalle_pagos (
    id_pago_detalle BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_empresa BIGINT NOT NULL,
    monto DECIMAL(15,2) NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    fecha_pago DATE NULL,
    refinanciado BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_empresa) REFERENCES empresa(id_empresa)
);

INSERT INTO empresa (nombre, rfc, fecha_registro) VALUES
('TechNova S.A.', 'TNV010101AA1', '2023-01-15'), 
('StartGrow S. de R.L.', 'SGR020202BB2', '2025-03-01'),
('FinTrust S.A.', 'FTR030303CC3', '2020-06-10'),
('LegalRisk S.A.', 'LRS040404DD4', '2022-05-20'),
('MaxCredit S.A.', 'MCS050505EE5', '2021-08-12'),
('AutoLease S.A.', 'ALS060606FF6', '2022-02-11'); 


INSERT INTO datos_contables (id_empresa, ventas_anuales, deuda_activa, flujo_efectivo, fecha_actualizacion) VALUES
(1, 12000000, 0, 2500000, '2025-09-15'), 
(2, 480000, 0, 100000, '2025-09-15'),
(3, 2400000, 0, 600000, '2025-09-15'),
(4, 3600000, 0, 800000, '2025-09-15'),
(5, 3000000, 500000, 400000, '2025-09-15'),
(6, 9000000, 0, 1500000, '2025-09-15');


INSERT INTO verificacion_legal (id_empresa, tiene_demandas, descripcion, fecha_verificacion) VALUES
(1, FALSE, 'Sin registros legales', '2025-09-15'),
(2, FALSE, 'Sin registros legales', '2025-09-15'),
(3, FALSE, 'Sin registros legales', '2025-09-15'),
(4, TRUE, 'Juicio mercantil abierto con proveedor externo', '2025-09-15'),
(5, FALSE, 'Sin registros legales', '2025-09-15'),
(6, FALSE, 'Sin registros legales', '2025-09-15');

INSERT INTO detalle_pagos (id_empresa, monto, fecha_vencimiento, fecha_pago, refinanciado)
VALUES
(1, 15000, '2024-10-01', '2024-10-01', FALSE),
(1, 15000, '2024-11-01', '2024-11-01', FALSE),
(1, 15000, '2024-12-01', '2024-12-01', FALSE),
(1, 15000, '2025-01-01', '2025-01-01', FALSE),
(1, 15000, '2025-02-01', '2025-02-01', FALSE),
(1, 15000, '2025-03-01', '2025-03-01', FALSE),
(1, 15000, '2025-04-01', '2025-04-01', FALSE),
(1, 15000, '2025-05-01', '2025-05-01', FALSE),
(1, 15000, '2025-06-01', '2025-06-01', FALSE),
(1, 15000, '2025-07-01', '2025-07-01', FALSE),
(1, 15000, '2025-08-01', '2025-08-01', FALSE),
(1, 15000, '2025-09-01', '2025-09-01', FALSE);


INSERT INTO detalle_pagos (id_empresa, monto, fecha_vencimiento, fecha_pago, refinanciado)
VALUES (2, 10000, '2025-09-01', '2025-09-01', FALSE);

INSERT INTO detalle_pagos (id_empresa, monto, fecha_vencimiento, fecha_pago, refinanciado)
VALUES (3, 12000, '2025-06-01', '2025-06-10', FALSE);

INSERT INTO detalle_pagos (id_empresa, monto, fecha_vencimiento, fecha_pago, refinanciado)
VALUES (4, 20000, '2025-07-01', '2025-07-01', FALSE);

INSERT INTO detalle_pagos (id_empresa, monto, fecha_vencimiento, fecha_pago, refinanciado)
VALUES (5, 25000, '2025-05-01', NULL, FALSE);

INSERT INTO detalle_pagos (id_empresa, monto, fecha_vencimiento, fecha_pago, refinanciado)
VALUES (6, 30000, '2025-08-01', '2025-08-01', FALSE);
