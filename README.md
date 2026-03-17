# Proyecto de Práctica

Este proyecto es una API REST desarrollada con **Spring Boot** como parte de práctica personal para reforzar conceptos de backend.

Incluye autenticación con **JWT** y manejo de sesiones mediante **Refresh Tokens**, además de gestión básica de usuarios y roles.

> ⚠️ **Nota:** Este proyecto fue creado con fines educativos, por lo que puede contener malas prácticas o implementaciones mejorables.

---

## 🧠 Funcionalidades

- 🔐 Autenticación con JWT  
- ♻️ Refresh Token para renovación de sesión  
- 👤 Gestión básica de usuarios  
- 🛡️ Control de roles y permisos  
- ⚠️ Manejo de excepciones  
- ✅ Validaciones básicas  

---

## 🛠️ Tecnologías

- Java  
- Spring Boot  
- Spring Security  
- JWT  
- JPA / Hibernate  
- Lombok  

---

## 🔑 Autenticación

El sistema utiliza dos tipos de tokens:

- **Access Token (JWT):**  
  Se usa para autenticar las peticiones. Tiene corta duración.

- **Refresh Token:**  
  Se utiliza para generar un nuevo access token sin necesidad de volver a iniciar sesión.

---

## 📌 Flujo básico de autenticación

1. El usuario inicia sesión  
2. El servidor devuelve:
   - Access Token  
   - Refresh Token  
3. El Access Token se usa en cada request  
4. Cuando expira:
   - Se envía el Refresh Token  
   - Se genera un nuevo Access Token  

---

## ▶️ Ejecución del proyecto

1. Clonar el repositorio:
```bash
git clone <tu-repo>
```

2. Configurar base de datos en `application.properties`

3. Ejecutar el proyecto:
```bash
./mvnw spring-boot:run
```

---

## 📚 Endpoints principales (ejemplo)

| Método | Endpoint         | Descripción        |
|--------|----------------|--------------------|
| POST   | /auth/login    | Iniciar sesión     |
| POST   | /auth/refreshToken  | Renovar token      |
| POST   | /auth/register | Registrar usuario  |

---

## 📌 Mejoras futuras

- Mejorar validaciones  
- Implementar tests  
- Optimizar manejo de errores  
- Mejorar arquitectura (clean architecture / hexagonal)  
- Seguridad más robusta  

---

## 🤓 Objetivo

El objetivo principal de este proyecto es practicar:

- Seguridad con Spring Security  
- Manejo de JWT y Refresh Tokens  
- Diseño de APIs REST  
- Buenas prácticas (aunque aún en proceso 😅)
