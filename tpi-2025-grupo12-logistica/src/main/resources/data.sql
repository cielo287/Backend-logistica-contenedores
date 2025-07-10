-- ====================
-- TARIFAS
-- ====================
INSERT INTO tarifa (monto_base, costo_por_km) VALUES 
  (5000.0, 120.0),
  (7000.0, 150.0);

-- ====================
-- SOLICITUDES
-- ====================
INSERT INTO solicitud (contenedor_id, ciudad_origen_id, ciudad_destino_id, deposito_id, camion_id, costo_estimado, tiempo_estimado_horas) VALUES 
  (1, 1, 3, 2, 1, 25000.0, 15.5),
  (2, 2, 4, 3, 2, 30000.0, 20.0);

-- ====================
-- TRAMOS DE RUTA
-- ====================
INSERT INTO tramo_ruta (solicitud_id, tipo_tramo, ciudad_origen_id, ciudad_destino_id, orden, fecha_estimada_salida, fecha_estimada_llegada, fecha_real_salida, fecha_real_llegada) VALUES
  (1, 'ORIGEN_DEP', 1, 2, 1, '2025-07-01', '2025-07-02', '2025-07-01', '2025-07-02'),
  (1, 'DEP_DESTINO', 2, 3, 2, '2025-07-03', '2025-07-04', '2025-07-03', '2025-07-04'),
  (2, 'ORIGEN_DEP', 2, 3, 1, '2025-07-05', '2025-07-06', '2025-07-05', '2025-07-06'),
  (2, 'DEP_DESTINO', 3, 4, 2, '2025-07-07', '2025-07-08', '2025-07-07', '2025-07-08');
