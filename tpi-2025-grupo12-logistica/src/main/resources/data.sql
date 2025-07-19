-- ====================
-- TARIFAS
-- ====================
INSERT INTO tarifa (monto_base, costo_por_km) VALUES 
  (5000.0, 120.0),
  (7000.0, 150.0);

-- ====================
-- SOLICITUDES
-- ====================
INSERT INTO solicitud (contenedor_id, ciudad_origen_id, ciudad_destino_id, deposito_id, camion_id, costo_estimado, tiempo_estimado_horas, fecha_estimada_despacho) VALUES 
  (1, 1, 3, 2, 1, 25000.0, 15.5, NULL),
  (2, 2, 4, 3, 2, 30000.0, 20.0, NULL);

-- ====================
-- TRAMOS DE RUTA
-- ====================
INSERT INTO tramo_ruta (
    solicitud_id,
    ubicacion_origen_id,
    ubicacion_destino_id,
    orden,
    fecha_real_salida,
    fecha_real_llegada,
    fecha_estimada_salida,
    fecha_estimada_llegada,
    distancia,
    tiempo_estimado
) VALUES
  (1, 1, 2, 1,'2025-07-02','2025-07-03', '2025-07-01', '2025-07-02', NULL, NULL),
  (1, 2, 3, 2, '2025-07-03', '2025-07-04',NULL,NULL, NULL, NULL),
  (2, 2, 3, 1, '2025-07-05', '2025-07-06', NULL,NULL,NULL, NULL),
  (2, 3, 4, 2, '2025-07-07', '2025-07-08',NULL,NULL, NULL, NULL);

