# Sistema de Inventario - Spring Boot

Aplicación web de gestión de inventario desarrollada con Spring Boot y Thymeleaf.

## Requisitos

- Java 17 o superior
- Maven 3.6 o superior

## Instalación

1. Clonar el repositorio
2. Navegar al directorio del proyecto
3. Ejecutar: `mvn clean install`

## Ejecución

```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## Credenciales de Acceso

| Usuario | Contraseña | Rol |
|---------|-----------|-----|
| admin   | admin123  | Administrador |
| user    | user123   | Usuario |

## Funcionalidades

- **Gestión de Productos**: Crear, editar, eliminar y listar productos
- **Gestión de Categorías**: Administrar categorías de productos (solo admin)
- **Carrito de Compras**: Agregar productos al carrito
- **Estadísticas**: Visualizar estadísticas del inventario (solo admin)
- **Seguridad**: Autenticación y autorización con Spring Security

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/inventario/
│   │   ├── controller/          # Controladores REST
│   │   ├── model/               # Modelos de datos
│   │   ├── repository/          # Acceso a datos
│   │   ├── service/             # Lógica de negocio
│   │   └── config/              # Configuración
│   └── resources/
│       ├── templates/           # Vistas HTML
│       ├── application.yml      # Configuración de aplicación
│       └── data.sql             # Datos iniciales
```

## Tecnologías Utilizadas

- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL/H2
- Bootstrap 5
