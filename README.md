# Product Service - Coffee Ecommerce

Documentación de servicio: Swagger

## Objetivo

Servicio encargado de la gestión del catálogo, inventario y reglas de negocio de productos para la plataforma Ecommerce https://github.com/fabetabilo/demo-coffee-ecommerce 

## Features

* **Gestión de Catálogo:** Administración de Cafés (con variantes/formatos), Accesorios y Packs de regalo.
* **Arquitectura DDD Pragmática:** Separación clara de contextos y lógica de negocio mediante Agregados.
* **Seguridad de Datos:** Implementación de **DTO Pattern** con separación estricta entre vistas públicas (Tienda) y privadas (Admin).
* **Control de Stock:** Lógica de disponibilidad calculada para clientes vs. inventario real para administradores.

* **Recomendaciones de Producto:** Lógica simple de recomendaciones que sugiere cafés según quiz.

## Stack Tecnológico

- Lenguaje: Java 21
- Framework: Spring Boot 3
- Persistencia: JPA / Hibernate
- Base de Datos: H2 (Dev)
- Gestor dependencias: Maven

## Controladores y Endpoints
Se implementó una segregación de endpoints basada en la audiencia:

* **Store API (`/api/v1/store`):** Solo lectura (`GET`). Optimizado para el frontend de clientes.
    * *Seguridad:* Oculta datos sensibles como el stock numérico exacto o costos.
    * *Respuesta:* Devuelve `PackDto`, `CoffeeDto` con flag `available: true/false`.
* **Admin API (`/api/v1/admin`):** Lectura y Escritura (`GET`, `POST`, `PUT`, `DELETE`).
    * *Seguridad:* (NO implementado) Requiere privilegios de administrador.
    * *Respuesta:* Devuelve `AdminPackDto`, `AdminCoffeeDto` con `stock: 15`.

* **Recommendation API (`/api/v1/recommendations`):** Recomendaciones personalizadas de producto.
    * *Método principal:* `POST /match` que recibe un `CoffeeQuizDto` y devuelve una lista de `CoffeeDto`.

## Endpoints

Utiliza Postman para pruebas: https://www.postman.com/

Notas rápidas:
- `Store` devuelve DTOs públicos con `available` calculado; oculta `stock`.
- `Admin` devuelve DTOs completos incluyendo `stock` y variantes con inventario.
- `Recommendation` devuelve lista de `CoffeeDto` públicos.

| API | Método | Ruta | Query Params | Descripción | DTO/Respuesta |
| - | - | - | - | - | - |
| Store | GET | `/api/v1/store/products/coffees` | `type` (opcional) | Lista cafés (filtra por subcategoría si se indica) | `CoffeeDto[]` |
| Store | GET | `/api/v1/store/products/accessories` | `type` (opcional) | Lista accesorios (filtra por subcategoría si se indica) | `AccessoryDto[]` |
| Store | GET | `/api/v1/store/products/packs` | — | Lista packs públicos | `PackDto[]` |
| Store | GET | `/api/v1/store/products/{id}` | — | Obtiene producto por `id`; retorna DTO según tipo | `CoffeeDto` / `AccessoryDto` / `PackDto` |
| Admin | GET | `/api/v1/admin/products/{id}` | — | Obtiene producto por `id` con datos completos | `AdminCoffeeDto` / `AdminAccessoryDto` / `AdminPackDto` |
| Admin | DELETE | `/api/v1/admin/products/{id}` | — | Elimina cualquier producto por `id` | `204 No Content` |
| Admin | GET | `/api/v1/admin/products/coffees` | — | Lista cafés con todos sus campos | `AdminCoffeeDto[]` |
| Admin | POST | `/api/v1/admin/products/coffees` | — | Crea un café | `AdminCoffeeDto` (`201 Created`) |
| Admin | PUT | `/api/v1/admin/products/coffees/{id}` | — | Actualiza un café existente | `AdminCoffeeDto` |
| Admin | GET | `/api/v1/admin/products/accessories` | — | Lista accesorios con todos sus campos | `AdminAccessoryDto[]` |
| Admin | POST | `/api/v1/admin/products/accessories` | — | Crea un accesorio | `AdminAccessoryDto` (`201 Created`) |
| Admin | PUT | `/api/v1/admin/products/accessories/{id}` | — | Actualiza un accesorio existente | `AdminAccessoryDto` |
| Admin | GET | `/api/v1/admin/products/packs` | — | Lista packs con todos sus campos | `AdminPackDto[]` |
| Admin | POST | `/api/v1/admin/products/packs` | — | Crea un pack | `AdminPackDto` (`201 Created`) |
| Admin | PUT | `/api/v1/admin/products/packs/{id}` | — | Actualiza un pack existente | `AdminPackDto` |
| Recommendation | POST | `/api/v1/recommendations/match` | — | Devuelve lista de cafés recomendados según quiz (`CoffeeQuizDto`) | `CoffeeDto[]` |


## Requisitos Previos

- **Java JDK 21**. Verifica versión:

```powershell
java -version
```

- **Maven Wrapper** (incluido: `mvnw`, `mvnw.cmd`). Opcionalmente Maven:

```powershell
./mvnw -v   # PowerShell en Windows usa .\mvnw.cmd
```

- **MySQL 8+** en local con una base de datos disponible.
    - Por defecto el proyecto usa: `jdbc:mysql://localhost:3306/mitienda_db`
    - Usuario: `root`, Password: vacío. Ajusta según tu entorno.

> Configuración actual en [src/main/resources/application.properties](src/main/resources/application.properties):
> - `spring.datasource.url=jdbc:mysql://localhost:3306/mitienda_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true`
> - `spring.datasource.username=root`
> - `spring.datasource.password=`
> - `spring.jpa.hibernate.ddl-auto=update`
> - `server.port=8080`


## Instalación y Ejecución

1) Crear base de datos (si no existe) en MySQL:

```sql
CREATE DATABASE mitienda_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2) Compilar y ejecutar con Maven Wrapper:

```powershell
./mvnw.cmd clean package
./mvnw.cmd spring-boot:run
```
Alternativa: ejecutar el JAR empaquetado (si prefieres no usar el plugin):

```powershell
java -jar target/product-service-0.0.1-SNAPSHOT.jar
```

3) Verificar que el servicio está arriba en `http://localhost:8080`.
