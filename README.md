# LoadBalancer-APIGateway-AccessToken

## 📋 Descripción del Proyecto

Este proyecto implementa un sistema de microservicios con balanceador de carga dinámico, API Gateway y autenticación por tokens JWT, desarrollado como parte de la materia de Ingeniería de Software II en la Universidad Pedagógica y Tecnológica de Colombia (UPTC).

## 🏗️ Arquitectura del Sistema

El proyecto está basado en el patrón de microservicios con los siguientes componentes:

- **Eureka Server**: Servidor de descubrimiento de servicios
- **Eureka Client**: Servicios que se registran en Eureka
- **Load Balancer**: Distribución de carga entre múltiples instancias
- **JWT Authentication**: Autenticación basada en tokens

## 👥 Equipo de Desarrollo

- **Gabriel Castillo** - Implementación del Load Balancer y API Gateway
- **Sebastián Cañón** - Implementación de la autenticación JWT
- **Jhon Castro** - Implementación del servidor Eureka y clientes

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Cloud 2023.0.1**
- **Netflix Eureka**
- **Spring Cloud LoadBalancer**
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **Maven**

## 📁 Estructura del Proyecto

```
LoadBalancer-APIGateway-AccessToken/
├── eureka-server/          # Servidor de descubrimiento de servicios
├── eureka-client/          # Cliente Eureka con endpoint /message
├── load-balancer-client/   # Cliente con balanceador de carga
└── pom.xml                 # Configuración principal de Maven
```

## 🚀 Instalación y Configuración

### Prerrequisitos

- Java 17 o superior
- Maven 3.6 o superior
- Git

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
git clone <url-del-repositorio>
cd LoadBalancer-APIGateway-AccessToken
```

2. **Compilar el proyecto**
```bash
mvn clean install
```

## 🏃‍♂️ Ejecución del Proyecto

### 1. Iniciar el Servidor Eureka
```bash
cd eureka-server
mvn spring-boot:run
```
- **Puerto**: 8761
- **Dashboard**: http://localhost:8761

### 2. Iniciar Múltiples Instancias del Cliente

**Terminal 1 (Puerto 8080):**
```bash
cd eureka-client
mvn spring-boot:run
```

**Terminal 2 (Puerto 8081):**
```bash
cd eureka-client
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

**Terminal 3 (Puerto 8082):**
```bash
cd eureka-client
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8082"
```

**Terminal 4 (Puerto 8083):**
```bash
cd eureka-client
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8083"
```

### 3. Iniciar el Cliente con Load Balancer
```bash
cd load-balancer-client
mvn spring-boot:run
```
- **Puerto**: 8084

## 🧪 Pruebas del Sistema

### Verificar Registro en Eureka
1. Acceder a http://localhost:8761
2. Verificar que aparezcan 4 instancias de `CLIENT-SERVICE`

### Probar Endpoints Individuales
```bash
# Instancia 1
curl http://localhost:8080/message

# Instancia 2
curl http://localhost:8081/message

# Instancia 3
curl http://localhost:8082/message

# Instancia 4
curl http://localhost:8083/message
```

### Probar Balanceador de Carga
```bash
# Múltiples peticiones para ver la distribución
curl http://localhost:8084/get-message
curl http://localhost:8084/get-message
curl http://localhost:8084/get-message
curl http://localhost:8084/get-message
```

### Probar Autenticación JWT
```bash
# Obtener token
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user","password":"password"}'

# Usar token en petición protegida
curl http://localhost:8080/message \
  -H "Authorization: Bearer <token>"
```

## 📊 Endpoints Disponibles

| Servicio | Endpoint | Descripción | Puerto |
|----------|----------|-------------|---------|
| Eureka Server | `/` | Dashboard de Eureka | 8761 |
| Client Service | `/message` | Endpoint protegido con JWT | 8080-8083 |
| Load Balancer | `/get-message` | Endpoint con balanceo de carga | 8084 |
| Authentication | `/auth/login` | Login para obtener JWT | 8080 |

## 🔧 Configuración

### Variables de Entorno
- `EUREKA_SERVER_URL`: URL del servidor Eureka (default: http://localhost:8761/eureka/)
- `JWT_SECRET`: Clave secreta para JWT
- `JWT_EXPIRATION`: Tiempo de expiración del token (default: 86400000ms)

### Archivos de Configuración
- `application.properties`: Configuración principal
- `application-{port}.properties`: Configuración específica por puerto

## 🧪 Testing

### Ejecutar Tests
```bash
# Todos los tests
mvn test

# Tests específicos
mvn test -Dtest=EurekaServerApplicationTests
```

## 📈 Monitoreo

### Health Checks
- **Eureka Dashboard**: http://localhost:8761
- **Actuator Endpoints**: http://localhost:8080/actuator/health

## 📚 Referencias

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)
- [Netflix Eureka](https://github.com/Netflix/eureka)
- [JWT.io](https://jwt.io/)

## 📄 Licencia

Este proyecto es desarrollado con fines académicos para la materia de Ingeniería de Software II de la UPTC.

---

**Universidad Pedagógica y Tecnológica de Colombia (UPTC)**  
**Ingeniería de Software II**  
**2025**
