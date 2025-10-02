# AutoService - Sistema de Gestión de Taller Mecánico

## 📋 Descripción

Sistema de gestión para taller mecánico que permite administrar citas, clientes y facturas. Proyecto desarrollado como ejemplo de refactoring de código legacy a arquitectura limpia y testeable.

## 🎯 Objetivo

Transformar código legacy no testeable en arquitectura limpia aplicando:
- Pruebas con BD transaccionales
- Diseño para testeabilidad  
- Test doubles efectivos
- Inyección de dependencias

## 🏗️ Arquitectura

src/
├── domain/ # Lógica de negocio
│ ├── model/ # Entidades (Appointment, Customer, Invoice)
│ ├── repository/ # Interfaces de repositorio
│ └── service/ # Servicios de dominio
├── infrastructure/ # Implementaciones concretas
│ ├── repository/ # Repositorios JPA
│ └── service/ # Servicios de infraestructura
└── web/ # Controladores REST y DTOs


## 🚀 Características

- **Gestión de Citas**: Crear y reagendar citas
- **Gestión de Clientes**: Registro automático o reutilización
- **Sistema de Facturación**: Generación de facturas
- **Notificaciones**: Envío de emails
- **Persistencia**: Base de datos H2 (desarrollo) / PostgreSQL (producción)

## 🛠️ Tecnologías

- **Java 21** con Spring Boot 3.2.0
- **Spring Data JPA** para persistencia
- **H2 Database** para desarrollo y testing
- **JUnit 5** + Mockito para testing
- **JaCoCo** para cobertura de código
- **Maven** para gestión de dependencias

## 📦 Instalación y Ejecución

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar tests
mvn test

# Ejecutar con cobertura
mvn test jacoco:report

# Ejecutar la aplicación
mvn spring-boot:run
