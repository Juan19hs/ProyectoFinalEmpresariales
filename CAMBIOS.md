# Sistema de Inventario - Spring Boot
## Mejoras Implementadas - Fase 1

### 📋 Cambios Realizados

#### 1. **Seguridad con Spring Security** ✅
- Agregadas dependencias de Spring Security
- Creada entidad `Usuario` con validaciones
- Implementado `UsuarioRepository` con métodos de búsqueda
- Creado `CustomUserDetailsService` para autenticación
- Configurado `SecurityConfig` con:
  - Autenticación mediante DAO
  - Cifrado de contraseñas con BCrypt
  - Rutas públicas (login) y protegidas
  - Logout con invalidación de sesión
  
#### 2. **Login y Autenticación** ✅
- Creada página de login mejorada con Bootstrap
- Controlador `LoginController` para manejar solicitudes
- Usuarios de prueba inicializados automáticamente:
  - Usuario: `admin` | Contraseña: `admin123` (Rol: ROLE_ADMIN)
  - Usuario: `user` | Contraseña: `user123` (Rol: ROLE_USER)

#### 3. **Frontend Mejorado con Bootstrap 5** ✅
- Actualizada plantilla de listado de productos:
  - Diseño responsivo y moderno
  - Navbar con información de usuario
  - Botón de logout
  - Tabla mejorada con iconos
  - Indicadores de stock bajo (< 10 unidades)
  
- Mejorado formulario de productos:
  - Validaciones JavaScript adicionales
  - Campos con iconos
  - Textos de ayuda
  - Diseño atractivo con gradiente
  - Mejor UX

#### 4. **Validaciones de Datos** ✅
- Agregadas anotaciones de validación en `Producto`:
  - `@NotBlank` para campos obligatorios
  - `@Size` para longitud de campos
  - `@Min` para valores numéricos
  
- Agregadas anotaciones en `Usuario`:
  - `@NotBlank`, `@Size`, `@Email`
  - Validación de email único
  - Validación de usuario único

#### 5. **Documentación JavaDoc** ✅
- Comentados todos los nuevos métodos
- Documentadas todas las clases
- Explicadas anotaciones y configuraciones
- Incluidos ejemplos de uso

### 🗄️ Archivos Creados/Modificados

**Nuevos archivos:**
- `src/main/java/com/inventario/model/Usuario.java` - Entidad Usuario
- `src/main/java/com/inventario/repository/UsuarioRepository.java` - Repositorio
- `src/main/java/com/inventario/service/CustomUserDetailsService.java` - Servicio de autenticación
- `src/main/java/com/inventario/config/SecurityConfig.java` - Configuración de seguridad
- `src/main/java/com/inventario/config/InitializationComponent.java` - Inicialización de datos
- `src/main/java/com/inventario/controller/LoginController.java` - Controlador de login
- `src/main/resources/templates/login.html` - Página de login

**Archivos modificados:**
- `pom.xml` - Agregadas dependencias de seguridad
- `src/main/java/com/inventario/controller/ProductoController.java` - Agregado JavaDoc
- `src/main/java/com/inventario/model/Producto.java` - Agregadas validaciones y JavaDoc
- `src/main/java/com/inventario/service/ProductoService.java` - Agregado JavaDoc
- `src/main/resources/templates/productos/listado.html` - Mejorado con Bootstrap y seguridad
- `src/main/resources/templates/productos/formulario.html` - Mejorado diseño y validaciones
- `src/main/resources/application.yml` - Configuración de logging

### 🚀 Cómo Usar

#### Compilar y ejecutar:
```bash
mvn clean install
mvn spring-boot:run
```

#### Acceder a la aplicación:
```
URL: http://localhost:9090
```

#### Credenciales de prueba:
- **Usuario admin:**
  - Usuario: `admin`
  - Contraseña: `admin123`
  
- **Usuario estándar:**
  - Usuario: `user`
  - Contraseña: `user123`

### 📊 Características Implementadas

- ✅ Login y logout seguro
- ✅ Protección de rutas con Spring Security
- ✅ Cifrado de contraseñas con BCrypt
- ✅ Validaciones de datos en el cliente y servidor
- ✅ Interfaz moderna con Bootstrap 5
- ✅ Información de usuario en navegación
- ✅ Manejo de mensajes flash para feedback
- ✅ Código completamente documentado con JavaDoc

### 🔒 Seguridad

- Las contraseñas se encriptan automáticamente con BCrypt
- Las rutas protegidas requieren autenticación
- Las sesiones se invalidan al logout
- Las validaciones se hacen en cliente y servidor

### 📝 Próximas Mejoras (Fase 2)

- Crear entidad `Categoría` con CRUD completo
- Agregar relación Producto-Categoría
- Implementar búsqueda y filtrado
- Agregar paginación
- Crear dashboard con estadísticas
- Exportar datos a CSV/PDF (opcional)

---

## Mejoras Implementadas - Fase 2

### 📋 Cambios Realizados

- Implementado CRUD de `Categoría` (solo administrador):
  - Se agregó la entidad `Categoria` con validaciones simples
  - Creado `CategoriaRepository`, `CategoriaService` y `CategoriaController` bajo `/admin/categorias`
  - Plantillas para listar y crear/editar categorías en `src/main/resources/templates/admin/categorias/`
  - Acceso restringido a `/admin/**` mediante Spring Security (ROLE_ADMIN)

- Añadido módulo de Estadísticas en el panel de administración:
  - `AdminController` con endpoint `/admin/estadisticas`
  - Métricas simples implementadas en `ProductoService`:
    - Productos más costosos
    - Productos más baratos
    - Productos con mayor stock
    - Productos con menor stock
  - Plantilla en `src/main/resources/templates/admin/estadisticas.html` para mostrar tabuladas las estadísticas

### 🗄️ Archivos Creados/Modificados (Fase 2)

**Nuevos archivos:**
- `src/main/java/com/inventario/model/Categoria.java` - Entidad Categoría
- `src/main/java/com/inventario/repository/CategoriaRepository.java` - Repositorio
- `src/main/java/com/inventario/service/CategoriaService.java` - Servicio de Categoría
- `src/main/java/com/inventario/controller/CategoriaController.java` - Controlador CRUD categorías (admin)
- `src/main/java/com/inventario/controller/AdminController.java` - Panel y estadísticas del admin
- `src/main/resources/templates/admin/panel.html` - Panel principal admin
- `src/main/resources/templates/admin/estadisticas.html` - Estadísticas de productos (admin)
- `src/main/resources/templates/admin/categorias/listado.html` - Listado de categorías
- `src/main/resources/templates/admin/categorias/formulario.html` - Formulario para crear/editar categoría

**Archivos modificados:**
- `src/main/java/com/inventario/service/ProductoService.java` - Añadidos métodos para obtener estadísticas de productos (obtenerMasCostosos, obtenerMasBaratos, obtenerMayorStock, obtenerMenorStock)
- `src/main/java/com/inventario/config/ConfiguracionSeguridad.java` - Añadida restricción para `/admin/**` (ROLE_ADMIN)
- `src/main/resources/templates/base.html` - Botón de navegación y enlace al panel de admin cuando el usuario es ROLE_ADMIN

### ✅ Comprobaciones realizadas

- Se verificó que `/admin/**` ahora está protegido y solo accesible con ROLE_ADMIN.
- Las plantillas del administrador se acceden desde el panel (`/admin`) y `/admin/estadisticas`.
- Las nuevas funcionalidades tienen JavaDoc y nombres en español.


**Autor:** Sistema de Inventario - Fase 2
**Fecha:** 2025
---

## Ajustes y Correcciones - Fase 2.1

### 📋 Cambios Realizados (UI y commits)

- Unificado el aspecto de las plantillas del administrador para reutilizar la interfaz base (`base.html`).
  - `admin/panel.html`, `admin/estadisticas.html`, `admin/categorias/listado.html`, y `admin/categorias/formulario.html` ahora usan `th:fragment="content"` y delegan el encabezado, navegación y estilos a `base.html`.
  - Se eliminaron duplicaciones de CSS y scripts en las plantillas del área de administración para mantener una interfaz coherente.
- Añadido el atributo `titulo` en `AdminController` y `CategoriaController` para que `base.html` muestre títulos adecuados en cada página administrativa.
- Documentación actualizada: esta sección y comentarios JavaDoc añadidos donde se modificaron los controladores para la nueva propiedad `titulo`.

### 🛠️ Archivos modificados (UI)
- `src/main/resources/templates/admin/panel.html` - Reutiliza `base.html`
- `src/main/resources/templates/admin/estadisticas.html` - Reutiliza `base.html`
- `src/main/resources/templates/admin/categorias/listado.html` - Reutiliza `base.html`
- `src/main/resources/templates/admin/categorias/formulario.html` - Reutiliza `base.html`
- `src/main/java/com/inventario/controller/AdminController.java` - Añadido `titulo` en métodos `panel` y `estadisticas`
- `src/main/java/com/inventario/controller/CategoriaController.java` - Añadido `titulo` en `listar`, `nuevoForm`, y `editarForm`

### 🖌️ Ajustes Visuales (Fase 2.2)

- Se aplicó el mismo estilo y cabecera que la vista de productos (`productos/listado.html`) y la de `login.html` a todas las vistas del área administrativa.
  - Añadido Bootstrap y Bootstrap Icons en las cabeceras de `admin/*` para que luzcan igual que las páginas principales.
  - Se incluyeron estilos CSS (gradiente, diseño de encabezado y contenedor) para mantener la estética existente.
  - Se agregó el botón de `Panel Admin` y la información de usuario en el encabezado para consistencia.

### ✅ Comprobaciones realizadas (Fase 2.2)
- Verificado que las páginas de administración (`/admin`, `/admin/estadisticas`, `/admin/categorias`) muestran el mismo estilo y cabecera que `productos/listado.html` y `login.html`.
- Comprobado que el acceso sigue protegido y que el contenido administra mantiene la interfaz coherente.

---

## Ajustes y Correcciones - Fase 2.3

### 📋 Cambios Realizados

- Corregido comportamiento al editar un producto que mostraba "Error: Código ya existe" cuando se guardaba un producto existente:
  - Ahora el formulario de edición envía la petición a `/productos/{id}` mediante `th:action` condicional en `productos/formulario.html`.
  - Se actualizó `ProductoService#actualizar` para permitir actualizar el campo `codigo` sólo si es único en la base de datos o si es el mismo código del producto editado.
- Eliminadas duplicaciones de la cabecera (usuario / cerrar sesión / enlace a Panel Admin) en las vistas administrativas para que esos controles solo aparezcan en el encabezado principal (`base.html`).

### 🗄️ Archivos modificados (Fase 2.3)
- `src/main/resources/templates/productos/formulario.html` - Acción del formulario condicional para edición/creación
- `src/main/java/com/inventario/service/ProductoService.java` - Validación de `codigo` durante actualización
- `src/main/resources/templates/admin/panel.html` - Eliminado perfil/Logout duplicado dentro del contenido
- `src/main/resources/templates/admin/estadisticas.html` - Eliminado perfil/Logout duplicado dentro del contenido
- `src/main/resources/templates/admin/categorias/listado.html` - Eliminado perfil/Logout duplicado dentro del contenido
- `src/main/resources/templates/admin/categorias/formulario.html` - Eliminado perfil/Logout duplicado dentro del contenido

### ✅ Comprobaciones realizadas (Fase 2.3)
- Probado localmente la edición de productos: ya no se produce el error de código al editar y guardar un producto existente.
- Comprobado que los controles de usuario y cerrar sesión solo aparecen en la cabecera principal y no están duplicados en las vistas administrativas.


### ✅ Comprobaciones realizadas
- Verificado que las plantillas del administrador hereden estilos y navegación de `base.html`.
- Verificado que las URLs administrativas sigan siendo `/admin/**` y que la protección por `ROLE_ADMIN` se mantiene.
---

**Versión:** 1.0  
**Autor:** Sistema de Inventario - Sexto Semestre  
**Fecha:** 2025
