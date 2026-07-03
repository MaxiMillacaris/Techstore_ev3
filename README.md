# TechStore Chile — Microservicio de Productos

**Asignatura:** JVY0101 — Java: Diseño y Construcción de Soluciones Nativas en Nube  
**Actividad:** EA2 — Evaluación Parcial N°2  
**Integrante:** Maximiliano Millacaris  

---

## Descripción

Microservicio RESTful desarrollado con Spring Boot que administra el catálogo de productos de la tienda TechStore Chile. Incluye autenticación JWT, CRUD completo con eliminación lógica, persistencia en PostgreSQL y orquestación con Docker Compose.

---

## Tecnologías

| Tecnología | Versión |
|---|---|
| Java | 17 |
| Spring Boot | 3.2.5 |
| Maven | 3.9.6 |
| PostgreSQL | 15 |
| Docker / Docker Compose | latest |
| JWT (jjwt) | 0.11.5 |

---

## Estructura del Proyecto (Arquitectura en Capas)

```
src/main/java/cl/techstore/api/
│
├── controller/
│   ├── AuthController.java        ← POST /auth/login
│   └── ProductoController.java   ← GET, POST, PUT, DELETE /api/productos
│
├── service/
│   └── ProductoService.java      ← Lógica de negocio del CRUD
│
├── repository/
│   └── ProductoRepository.java   ← Extiende JpaRepository
│
├── model/
│   └── Producto.java             ← Entidad JPA (@Entity)
│
├── security/
│   ├── JwtUtil.java              ← Generación y validación de tokens
│   ├── JwtFilter.java            ← Filtro que intercepta cada petición
│   └── SecurityConfig.java       ← Configuración Spring Security
│
└── dto/
    ├── LoginRequest.java          ← { username, password }
    ├── LoginResponse.java         ← { token, tipo, expiracion }
    └── ProductoDTO.java           ← Objeto de transferencia para productos
```

---

## Requisitos Previos

- Java 17
- Maven 3.9+
- Docker Desktop instalado y corriendo
- Git

---

## Clonar el Repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
cd techstore-api
```

---

## Opción 1 — Ejecutar con Docker Compose (Recomendado)

Levanta PostgreSQL y el microservicio en un solo comando:

```bash
docker compose up --build
```

Detener los servicios:

```bash
docker compose down
```

---

## Opción 2 — Ejecutar Localmente (requiere PostgreSQL)

### 1. Levantar PostgreSQL con Docker

```bash
docker run --name techstore_db \
  -e POSTGRES_DB=techstore \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin123 \
  -p 5432:5432 \
  -d postgres:15
```

### 2. Compilar y empaquetar con Maven

```bash
# Compilar
mvn compile

# Ejecutar tests
mvn test

# Generar el .JAR en target/
mvn clean package -DskipTests
```

### 3. Ejecutar el JAR

```bash
java -jar target/techstore-api-1.0.0.jar
```

La aplicación queda disponible en `http://localhost:8080`.

---

## Uso de la API con Postman

### Paso 1 — Obtener Token JWT

```
POST http://localhost:8080/auth/login
Content-Type: application/json

{
  "username": "admin@techstore.cl",
  "password": "Admin1234"
}
```

Respuesta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer",
  "expiracion": "3600"
}
```

### Paso 2 — Usar el Token

En todas las siguientes peticiones, agrega el header:
```
Authorization: Bearer <token_obtenido>
```

### Endpoints de Productos

| Método | Endpoint | Código HTTP | Descripción |
|---|---|---|---|
| GET | `/api/productos` | 200 OK | Listar todos los activos |
| GET | `/api/productos/{id}` | 200 OK | Obtener uno por ID |
| POST | `/api/productos` | 201 Created | Crear producto |
| PUT | `/api/productos/{id}` | 200 OK | Modificar producto |
| DELETE | `/api/productos/{id}` | 204 No Content | Eliminación lógica |

### Cuerpo JSON para crear/modificar

```json
{
  "nombre": "Laptop Lenovo IdeaPad",
  "descripcion": "Notebook 15.6 pulgadas, 8GB RAM, 512GB SSD",
  "precio": 499990,
  "stock": 15,
  "categoria": "Computación",
  "activo": true
}
```

---

## Verificación Final

- [ ] `mvn clean package` genera `target/techstore-api-1.0.0.jar` sin errores
- [ ] `docker compose up --build` levanta ambos contenedores
- [ ] `POST /auth/login` devuelve token JWT
- [ ] `GET /api/productos` retorna lista (vacía inicialmente)
- [ ] `POST /api/productos` crea producto con HTTP 201
- [ ] `PUT /api/productos/{id}` modifica con HTTP 200
- [ ] `DELETE /api/productos/{id}` cambia `activo` a `false` con HTTP 204

---

## Control de Versiones (Git)

```bash
# Inicializar y subir rama DEV
git init
git checkout -b DEV
git add .
git commit -m "feat: implementación inicial del microservicio TechStore"
git remote add origin <URL>
git push origin DEV

# Merge a MAIN al finalizar
git checkout main
git merge DEV
git push origin main
```
