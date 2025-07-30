# Backend de gestión de logística terrestre de contendedores 🛣️🚚📍

Este backend gestiona las solicitudes de transporte de contendedores realizada por un cleinte, generando todas las funcionalidades necesarias para calcular la ruta mas eficiente y el seguimiento del contendedor, además de generar reportes sobre el desempeño del servicio.

## 🧪 Tecnologías utilizadas

- **Java 21+**
- **Spring Boot** y **Spring WebFlux**
- **Spring Security** + **JWT**
- **Keycloak** para gestión de usuarios y roles
- **Docker** y **Docker Compose**
- **Maven** como gestor de dependencias
- **H2 (en memoria)** como base de datos para testeo
- **Swagger** para la documentación de los Endpoints
- **Git** para el control de versiones

## 🛠️ Arquitectura y Microservicios

| Microservicio     | URL Local         | Descripción                                 |
|-------------------|-------------------|---------------------------------------------|
| **Gateway**       | `localhost:8082`  | Punto de entrada para el enrutamiento       |
| **Logistica**     | `localhost:8081`  | Gestión de la logistica del traslado        |
| **Pedidos**       | `localhost:8080`  | Gestión de los pedidos de transporte        |

## 🔑 Autenticación y Autorización

El sistema utiliza **Keycloak** para la autenticación mediante **tokens JWT**. Dependiendo del rol del usuario (`ADMIN`, `CLIENTE`), se otorgan permisos para acceder a diferentes endpoints.

# Usuarios, contraseñas y roles

| Usuario  | Contraseña         | Rol      |
|----------|--------------------|----------|
| admin01  | admin123           | Admin    |
| cliente01| cliente123         | Cliente  |

## 📚 Endpoints Principales y Ejemplos de Uso

### ** Microservicio Logística **.

1. ** Solicitar una petición de traslado **

- **POST** api/logistica/solicitudes
- **Body**(JSON)
{
    "idContenedor": 10,
    "idCiudadOrigen": 1,
    "idCiudadDestino": 2
}

- **Autenticación**: `ADMIN`, `CLIENTE`
⚠️ El contenedor y las ciudades deben existir previamente en la base de datos. Se pueden crear desde el Microservicio de Pedidos:
POST /api/pedidos/contenedores
POST /api/pedidos/ciudades

2. ** Procesar las solicitudes de traslado **

- **PUT** api/logistica/solicitudes/{id}/procesar-solicitudes
- **Body**(JSON)

{
    "FechaEstimadaDespacho": "2025-08-10",
    "camiónId": 1,
    "depositoId": 2
}
 - ** Autenticación** : `ADMIN`

⚠️ El depósito y el camión deben existir previamente en la base de datos. Se crean desde:
 POST /api/pedidos/depositos
 POST /api/pedidos/camiones

🔄 Este endpoint genera los TramoRuta correspondientes usando la API de Google Distance Matrix, estableciendo fechas estimadas de llegada/salida y costos.


3. ** Consulta del Estado de la solicitud **

 - **GET** api/logistica/solicitudes/{id}/resumen-cliente
 - ** Autenticación **: `ADMIN`, `CLIENTE`

4. ** Informe de desempeño del servicio **

 - **GET** api/solicitudes/informe-desempeno

 - ** Autenticación ** : `ADMIN`

### ** Microservicio Pedidos **.

1. ** Cambio de Estado de un Contenedor **

 - **PUT** api/pedidos/contenedores/{id}/estado

 - **Body**(JSON)

{
    "id": 1 
}

** Autenticación **: `ADMIN`

📡 Este cambio de estado emite un evento hacia el microservicio de logística para actualizar fechas reales y estado de la solicitud asociada.

📌 Notas Finales

📌 **Notas Finales**

- Todos los endpoints requieren autenticación vía token JWT.
- Swagger UI está disponible en cada microservicio en la ruta `/swagger-ui.html` (si está habilitado).
- En entorno local, Keycloak se puede acceder desde: `http://localhost:8083`.
- Para correr el entorno local, es necesario tener **Docker Desktop** instalado.  
  Luego, desde la carpeta `/infra`, ejecutar:
  ```bash
  docker compose up -d para levantar el servidor de Keycloak y docker compose down para deternerlo.
