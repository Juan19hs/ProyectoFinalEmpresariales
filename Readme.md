# Sistema de Inventario - Spring Boot

---

## Características Principales

### 1. Autenticación y Seguridad
- Login seguro con Spring Security
- Contraseñas encriptadas con BCrypt
- Dos roles de usuario: **Administrador** y **Usuario Estándar**
- Sesiones y logout automático
- Protección de rutas según roles

### 2. Gestión de Productos
- Crear, editar, listar y eliminar productos
- Asignación de categorías a cada producto
- Validaciones de datos (código único, precio mínimo, etc.)
- Indicadores visuales de stock bajo (< 10 unidades)

### 3. Gestión de Categorías (Admin)
- CRUD completo de categorías
- Asignación de categorías a productos
- Panel administrativo

### 4. Estadísticas (Admin)
- Productos más costosos
- Productos más baratos
- Productos con mayor/menor stock

### 5. Interfaz
- Bootstrap 5 
- Navbar con información de usuario
- Tablas con iconos y acciones rápidas
- Formularios validados

---

## Arquitectura del Sistema

```
┌─────────────────────────────────────────────────────────────┐
│                        NAVEGADOR                            │
│                                                             │
│  [Páginas HTML con Thymeleaf + Bootstrap 5]                 │
│  - login.html                                               │
│  - productos/listado.html, productos/formulario.html        │
│  - admin/panel.html, admin/estadisticas.html                │
│  - admin/categorias/listado.html, formulario.html           │
└──────────────────────┬──────────────────────────────────────┘
                       │ HTTP (Solicitudes)
                       ▼
┌─────────────────────────────────────────────────────────────┐
│             CONTROLADORES (Controller Layer)                │
│                                                             │
│  - ProductoController (/productos)                          │
│  - ControladorLogin (/login)                                │
│  - AdminController (/admin)                                 │
│  - ControladorCategoria (/admin/categorias)                 │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│              SERVICIOS (Service Layer)                      │
│                                                             │
│  - ProductoService        [Lógica de negocios de productos] │
│  - ServicioCategoria      [Lógica de categorías]            │
│  - ServicioDetallesUsuario [Autenticación]                  │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│            REPOSITORIOS (Data Access Layer)                 │
│                                                             │
│  - ProductoRepository     [JpaRepository<Producto>]         │
│  - CategoriaRepository    [JpaRepository<Categoria>]        │
│  - UsuarioRepository      [JpaRepository<Usuario>]          │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                BASE DE DATOS MySQL                          │
│                                                             │
│  - Tabla: usuarios        [id, username, email, password]   │
│  - Tabla: productos       [id, codigo, nombre, precio, ...] │
│  - Tabla: categorias      [id, nombre, descripcion]         │
└─────────────────────────────────────────────────────────────┘
```

---

## Configuración: 

**Configurar la base de datos** (archivo `src/main/resources/application.yml`):
---
  -yaml
  
       url: jdbc:mysql://localhost:3306/inventario
       username: root
       password: tu_contraseña


**Compilar y ejecutar**:
   bash
   mvn spring-boot:run
   

**Acceder a la aplicación**:http://localhost:9090/productos

---

## Usuarios de Prueba
```
| Usuario | Contraseña | Rol          |
|---------|-----------|---------------|
| admin   | admin123  | Administrador |
| user    | user123   | Usuario       |

```

## Estructura del Proyecto

```
src/main/
├── java/com/inventario/
│   ├── model/           [Entidades: Producto, Usuario, Categoria]
│   ├── repository/      [Acceso a datos: JPA Repositories]
│   ├── service/         [Lógica de negocios]
│   ├── controller/      [Controladores REST]
│   └── config/          [Spring Security, Inicialización]
└── resources/
    ├── templates/       [Vistas Thymeleaf]
    ├── static/          [CSS, JS, imágenes]
    └── application.yml  [Configuración]
```

---

## Funcionalidades Principales

### Para Usuarios
- Ver listado de productos
- Ver detalles y búsqueda básica

### Para Administradores (además de lo anterior)
- Crear, editar y eliminar productos
- Gestionar categorías
- Ver estadísticas de inventario
- Acceso al panel administrativo

## Autores
-Juan Hernández, Sergio Luna, José Ordoñez

---
