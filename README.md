# LoadBalancer-APIGateway-AccessToken

## 📋 Descripción del Proyecto

Este proyecto implementa un sistema básico de microservicios usando Netflix Eureka para el descubrimiento de servicios, desarrollado como parte de la materia de Ingeniería de Software II en la Universidad Pedagógica y Tecnológica de Colombia (UPTC).

## 🏗️ Arquitectura del Sistema

El proyecto está basado en el patrón de microservicios con los siguientes componentes implementados:

- **Eureka Server**: Servidor de descubrimiento de servicios
- **Eureka Client**: Servicio que se registra en Eureka con endpoint básico
- **Load Balancer**: Cliente que distribuye la carga entre las instancias del cliente Eureka

## 👥 Equipo de Desarrollo

- **Gabriel Castillo** - Implementación del Load Balancer y API Gateway
- **Sebastián Cañón** - Implementación de la autenticación JWT
- **Jhon Castro** - Implementación del servidor Eureka y clientes

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Cloud 2023.0.1**
- **Netflix Eureka**
- **Maven**

## 📁 Estructura del Proyecto

```
LoadBalancer-APIGateway-AccessToken/
├── eureka-server/          # Servidor de descubrimiento de servicios
├── eureka-client/          # Cliente Eureka con endpoint /message
├── load-balancer/          # Cliente con balanceador de carga
└── pom.xml                 # Configuración principal de Maven
```

## 🚀 Instalación y Configuración

### Prerrequisitos

- Java 17 o superior
- Maven 3.6 o superior
- Git

### Pasos de Instalación

1. **Clonar el repositorio**

```powershell
git clone <url-del-repositorio>
cd LoadBalancer-APIGateway-AccessToken
```

2. **Compilar cada proyecto por separado**

```powershell
# Eureka Server
cd eureka-server
mvn clean install
cd ..

# Eureka Client
cd eureka-client
mvn clean install
cd ..

# Load Balancer
cd load-balancer
mvn clean install
cd ..
```

## 🏃‍♂️ Ejecución del Proyecto

### Comandos de Ejecución (Proyectos Independientes)

| Tipo de Servicio   | Puerto | Comando                                                                                               | Terminal   |
| ------------------ | ------ | ----------------------------------------------------------------------------------------------------- | ---------- |
| **Eureka Server**  | 8761   | `cd eureka-server && mvn spring-boot:run`                                                             | Terminal 1 |
| **Cliente Eureka** | 8080   | `cd eureka-client && mvn spring-boot:run`                                                             | Terminal 2 |
| **Cliente Eureka** | 8081   | `cd eureka-client && mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=8081"` | Terminal 3 |
| **Cliente Eureka** | 8082   | `cd eureka-client && mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=8082"` | Terminal 4 |
| **Cliente Eureka** | 8083   | `cd eureka-client && mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=8083"` | Terminal 5 |
| **Load Balancer**  | 8084   | `cd load-balancer && mvn spring-boot:run`                                                             | Terminal 6 |

### 1. Iniciar el Servidor Eureka

```powershell
# Ir al directorio del eureka-server
cd eureka-server
mvn spring-boot:run
```

- **Puerto**: 8761
- **Dashboard**: <http://localhost:8761>

### 2. Iniciar Múltiples Instancias del Cliente

**Terminal 1 (Puerto 8080 - Perfil por defecto):**

```powershell
cd eureka-client
mvn spring-boot:run
```

**Terminal 2 (Puerto 8081):**

```powershell
cd eureka-client
mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=8081"
```

**Terminal 3 (Puerto 8082):**

```powershell
cd eureka-client
mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=8082"
```

**Terminal 4 (Puerto 8083):**

```powershell
cd eureka-client
mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=8083"
```

### 3. Iniciar el Load Balancer

```powershell
cd load-balancer
mvn spring-boot:run
```

### 3. Iniciar el Load Balancer

```powershell
mvn spring-boot:run -pl load-balancer
```

- **Puerto**: 8084

## 🧪 Pruebas del Sistema

### Verificar Registro en Eureka

1. Acceder a <http://localhost:8761>
2. Verificar que aparezcan las instancias de `CLIENT-SERVICE` registradas

### Probar Endpoints del Cliente

```powershell
# Instancia 1 (Puerto 8080)
curl http://localhost:8080/message

# Instancia 2 (Puerto 8081)
curl http://localhost:8081/message

# Instancia 3 (Puerto 8082)
curl http://localhost:8082/message

# Instancia 4 (Puerto 8083)
curl http://localhost:8083/message
```

**Respuesta esperada:** `Hello! Eureka client from PORT number :[puerto]`

### Probar Load Balancer

```powershell
# Múltiples peticiones para ver la distribución de carga
curl http://localhost:8084/get-message
curl http://localhost:8084/get-message
curl http://localhost:8084/get-message
curl http://localhost:8084/get-message
```

**Respuesta esperada:** `Hello! Eureka client from PORT number :[puerto]` (el puerto cambiará con cada petición)

## 📊 Endpoints Disponibles

| Servicio        | Endpoint       | Descripción                        | Puerto    |
| --------------- | -------------- | ---------------------------------- | --------- |
| Eureka Server   | `/`            | Dashboard de Eureka                | 8761      |
| Client Service  | `/message`     | Mensaje con información del puerto | 8080-8083 |
| Message Service | `/get-message` | Distribuye carga entre clientes    | 8084      |

## 🔧 Configuración

### Archivos de Configuración YAML

El proyecto utiliza archivos `.yml` para una configuración más limpia y legible:

#### Eureka Server (`eureka-server/src/main/resources/application.yml`)

```yaml
server:
  port: 8761

eureka:
  client:
    register-with-eureka: false # El servidor no se registra a sí mismo
    fetch-registry: false # El servidor no consulta el registro
  server:
    enable-self-preservation: false # Opcional: Deshabilitar auto-preservación durante pruebas

spring:
  application:
    name: eureka-server
```

#### Eureka Client - Configuración Base (`eureka-client/src/main/resources/application.yml`)

```yaml
server:
  port: 8080 # Puerto por defecto

spring:
  application:
    name: client-service # Nombre lógico del servicio

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/ # URL del Servidor Eureka
  instance:
    prefer-ip-address: true # Usar IP en lugar de hostname
    lease-renewal-interval-in-seconds: 30
    lease-expiration-duration-in-seconds: 90
```

#### Perfiles Específicos por Puerto

- `application-8081.yml` - Puerto 8081
- `application-8082.yml` - Puerto 8082
- `application-8083.yml` - Puerto 8083

### Variables de Entorno

- `EUREKA_SERVER_URL`: URL del servidor Eureka (default: <http://localhost:8761/eureka/>)

### Archivos de Configuración

- `application.yml`: Configuración principal
- `application-{port}.yml`: Configuración específica por puerto

## 🧪 Testing

### Ejecutar Tests (Proyectos Independientes)

```powershell
# Test Eureka Server
cd eureka-server
mvn test
cd ..

# Test Eureka Client
cd eureka-client
mvn test
cd ..

# Test Load Balancer
cd load-balancer
mvn test
cd ..
```

## 📈 Monitoreo

### Health Checks

- **Eureka Dashboard**: <http://localhost:8761>
- **Actuator Endpoints**: <http://localhost:8080/actuator/health>

## ✅ Resumen de Cambios Realizados

### Estructura de Proyectos Independientes

- ✅ **Eliminado POM padre**: Cada proyecto tiene su propia configuración Maven independiente
- ✅ **Java 17**: Configurado para compatibilidad con el sistema actual
- ✅ **Spring Boot 3.4.0**: Versión unificada en todos los proyectos
- ✅ **Spring Cloud 2024.0.0-RC1**: Versión consistente para service discovery

### Comandos de Ejecución Corregidos

- ✅ **Sintaxis Maven**: Comandos corregidos para perfiles de Spring Boot
- ✅ **Proyectos Independientes**: Cada servicio se ejecuta desde su propio directorio
- ✅ **Compilación Verificada**: Todos los proyectos compilan exitosamente

### Servicios Configurados

- ✅ **Eureka Server**: Puerto 8761 - Dashboard disponible
- ✅ **Eureka Client**: Puertos 8080-8083 - Múltiples instancias
- ✅ **Load Balancer**: Puerto 8084 - Distribución de carga

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
