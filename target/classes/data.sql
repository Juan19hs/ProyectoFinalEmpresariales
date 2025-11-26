/**
 * Script SQL para inicializar datos de prueba.
 * 
 * Este script se ejecuta automáticamente cuando la aplicación inicia.
 * Crea usuarios de prueba con contraseñas encriptadas usando BCrypt.
 * 
 * Usuario admin: admin / admin123
 * Usuario user: user / user123
 * 
 * Nota: Las contraseñas están encriptadas con BCrypt (fuerza 10)
 * 
 * @author Sistema de Inventario
 * @version 1.0
 * @since 2025
 */

-- Insertar usuario admin si no existe
INSERT IGNORE INTO usuarios (username, email, password, nombre_completo, activo, rol)
VALUES (
  'admin',
  'admin@inventario.com',
  '$2a$10$XeDzfSbELrxCfj4T3rwN5enX2uZF8qQkL0I5sLmqKV4sIxEV4HYKC',
  'Usuario Administrador',
  true,
  'ROLE_ADMIN'
);

-- Insertar usuario normal si no existe
INSERT IGNORE INTO usuarios (username, email, password, nombre_completo, activo, rol)
VALUES (
  'user',
  'user@inventario.com',
  '$2a$10$bW7sKcwBGJfbSGZrxL.O/uMWQ4w0zY.H2gPYxLDzpDlPMZ7uBJ1y2',
  'Usuario Estándar',
  true,
  'ROLE_USER'
);
