-- Agrega la columna fecha_publicacion si no existe
ALTER TABLE puesto
    ADD COLUMN IF NOT EXISTS fecha_publicacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Rellena filas existentes que queden NULL
UPDATE puesto
SET fecha_publicacion = CURRENT_TIMESTAMP
WHERE fecha_publicacion IS NULL;
