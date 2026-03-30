# Programacion4-Proyecto1 - Bolsa de Empleo

Aplicacion web SSR (Server-Side Rendering) desarrollada con Spring Boot para gestionar una bolsa de empleo con tres actores: administrador, empresas y oferentes.

## Tabla de contenido

- [Descripcion](#descripcion)
- [Objetivo](#objetivo)
- [Tecnologias](#tecnologias)
- [Arquitectura](#arquitectura)
- [Modulos funcionales](#modulos-funcionales)
- [Motor de coincidencia de candidatos](#motor-de-coincidencia-de-candidatos)
- [Modelo de datos](#modelo-de-datos)
- [Configuracion por perfiles](#configuracion-por-perfiles)
- [Ejecucion local](#ejecucion-local)
- [Ejecucion con Docker](#ejecucion-con-docker)
- [Rutas principales](#rutas-principales)
- [Sesiones y control de acceso](#sesiones-y-control-de-acceso)
- [Manejo de errores](#manejo-de-errores)
- [Validaciones](#validaciones)
- [Migraciones](#migraciones)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Puntos de inspeccion tecnica](#puntos-de-inspeccion-tecnica)
- [Mejoras sugeridas](#mejoras-sugeridas)

## Descripcion

El sistema permite:

- Publicar ofertas laborales con requisitos por habilidad.
- Registrar oferentes con habilidades y CV.
- Aprobar cuentas (empresa/oferente) desde un rol administrador.
- Calcular porcentaje de coincidencias entre puestos y candidatos.

## Objetivo

Apoyar el proceso de reclutamiento conectando empresas y oferentes mediante un esquema de habilidades con niveles (1 a 5), priorizando candidatos según compatibilidad con cada puesto.

## Tecnologias

- Java 21
- Spring Boot 3.2.5
- Spring Web (MVC)
- Spring Data JPA
- Thymeleaf
- Bean Validation
- PostgreSQL
- Maven Wrapper (`mvnw`, `mvnw.cmd`)
- Docker y Docker Compose

## Arquitectura

Capas principales:

- `controller`: endpoints web, formularios, sesiones, redirecciones.
- `service`: reglas de negocio y coordinacion de casos de uso.
- `repository`: acceso a BD y consultas JPA/JPQL.
- `model`: entidades persistentes y relaciones.
- `dto`: objetos para formularios, validacion y salida de vistas.
- `resources/templates`: vistas Thymeleaf.
- `resources/static`: estilos e imagenes.

## Modulos funcionales

### 1. Inicio público

- Lista los ultimos 5 puestos publicos activos.
- Muestra detalle de puesto.
- Permite buscar puestos por caracteristicas.

### 2. Administrador

- Login/logout.
- Aprobacion de empresas pendientes.
- Aprobacion de oferentes pendientes.
- Gestion de arbol de caracteristicas.
- Reporte mensual por `YYYY-MM`.

### 3. Empresa

- Registro y login (requiere aprobacion previa).
- Dashboard con puestos propios.
- Publicacion de puestos (`publico` o `privado`).
- Asignacion de habilidades requeridas por puesto.
- Desactivacion de puestos.
- Consulta de candidatos y descarga de CV.

### 4. Oferente

- Registro y login (requiere aprobacion previa).
- Dashboard de perfil.
- Registro de habilidades por nivel.
- Subida de CV en PDF (almacenado en BD).

## Motor de coincidencia de candidatos

Logica principal (en `PuestoService#calcularCandidatos`):

1. Obtiene requisitos del puesto (caracteristica + nivel minimo).
2. Busca oferentes aprobados que cumplan al menos un requisito.
3. Calcula porcentaje de coincidencias:

```text
(habilidades_cumplidas / total_requisitos) * 100
```

4. Ordena candidatos de mayor a menor porcentaje.

## Modelo de datos

Entidades:

- `Administrador`
- `Empresa`
- `Oferente`
- `Puesto`
- `Caracteristica`
- `PuestoHabilidad` (N:M entre Puesto y Caracteristica, con nivel)
- `OferenteHabilidad` (N:M entre Oferente y Caracteristica, con nivel)

Relaciones clave:

- `Empresa 1:N Puesto`
- `Puesto N:M Caracteristica` via `PuestoHabilidad`
- `Oferente N:M Caracteristica` via `OferenteHabilidad`
- `Caracteristica` con jerarquia `padre -> subcaracteristicas`

## Configuracion por perfiles

### `application-dev.properties`

Configuracion local:

- URL: `jdbc:postgresql://localhost:5432/bolsa_empleo`
- Usuario: `postgres`
- Password: `postgres`
- `spring.jpa.hibernate.ddl-auto=update`

### `application-prod.properties`

Usa variables de entorno:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `spring.jpa.hibernate.ddl-auto=none`

## Ejecucion local

Requisitos:

- Java 21
- PostgreSQL 15+

Pasos:

1. Crear BD `bolsa_empleo`.
2. Verificar credenciales del perfil `dev`.
3. Ejecutar la app:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

En Windows (wrapper):

```bash
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

Acceso: `http://localhost:8080`

## Ejecucion con Docker

`docker-compose.yml` levanta:

- PostgreSQL (`localhost:5432`)
- pgAdmin (`http://localhost:8081`)

Comando:

```bash
docker compose up -d
```

Credenciales del compose actual:

- DB: `bolsa_empleo`
- Usuario: `admin`
- Password: `admin`

Nota: el perfil `dev` usa `postgres/postgres`. Ajusta uno de los dos lados para que coincidan.

## Rutas principales

Publicas:

- `GET /`
- `GET /puesto/{id}`
- `GET /puestos/buscar-por-caracteristicas`

Administrador:

- `GET/POST /admin/login`
- `GET /admin/dashboard`
- `POST /admin/empresa/aprobar/{id}`
- `GET /admin/oferentes`
- `POST /admin/oferente/aprobar/{id}`
- `GET /admin/caracteristicas`
- `POST /admin/caracteristicas/nueva`
- `GET /admin/reporte?mes=YYYY-MM`

Empresa:

- `GET/POST /empresa/registro`
- `GET/POST /empresa/login`
- `GET /empresa/dashboard`
- `GET /empresa/puesto/nuevo`
- `POST /empresa/puesto/publicar`
- `POST /empresa/puesto/desactivar/{id}`
- `GET /empresa/puesto/candidatos/{id}`
- `GET /empresa/candidato/{oferenteId}`
- `GET /empresa/candidato/{oferenteId}/cv`

Oferente:

- `GET/POST /oferente/registro`
- `GET/POST /oferente/login`
- `GET /oferente/dashboard`
- `POST /oferente/habilidad/agregar`
- `POST /oferente/cv/subir`

## Sesiones y control de acceso

Se usa `HttpSession` con estos atributos:

- `adminId`
- `empresaId`
- `oferenteId`

Si no existe sesion válida para el módulo, se redirige al login correspondiente.

## Manejo de errores

`GlobalExceptionHandler` centraliza errores y renderiza `error.html` para:

- `IllegalArgumentException`
- `IllegalStateException`
- `Exception` (generico)

## Validaciones

- Correo válido en formularios de registro.
- Password minimo de 6 caracteres.
- Nivel de habilidades de oferente: 1 a 5.
- Nivel en publicacion de puesto: 0 a 5 (0 = no requerido).
- Tipo de publicacion permitido: `publico` o `privado`.

## Migraciones

Script incluido:

- `src/main/resources/db/migration/V1__add_fecha_publicacion.sql`

Funcion:

- Agrega `fecha_publicacion` en `puesto` si no existe.
- Completa valores nulos con `CURRENT_TIMESTAMP`.

## Estructura del proyecto

```text
src/main/java/cr/ac/una/
  Main.java
  controller/
  service/
  repository/
  model/
  dto/

src/main/resources/
  application.properties
  application-dev.properties
  application-prod.properties
  db/migration/
  templates/
  static/
```

## Puntos de inspeccion tecnica

Aspectos que vale la pena revisar al auditar el proyecto:

1. Seguridad de credenciales: passwords en texto plano.
2. Seguridad de acceso: falta Spring Security/autorizacion declarativa.
3. Validacion de CV: no se valida MIME/tamano en backend.
4. Integridad de registro de empresa: se guarda `direccion` vacia en el controller.
5. Pruebas: no hay suite en `src/test`.
6. Datos iniciales: no hay seed de administrador por defecto.

## Mejoras sugeridas

- Implementar Spring Security + `BCryptPasswordEncoder`.
- Agregar tests unitarios e integracion para servicios criticos.
- Crear migraciones/seeds de datos base.
- Agregar auditoria de entidades (creacion/actualizacion).
- Separar capa web y API REST para futuras integraciones.

---
Para inspeccion rapida del negocio, revisa primero:

- `src/main/java/cr/ac/una/service/PuestoService.java`
- `src/main/java/cr/ac/una/controller/AdminController.java`
- `src/main/java/cr/ac/una/repository/PuestoRepository.java`
- `src/main/java/cr/ac/una/repository/OferenteRepository.java`
