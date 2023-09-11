## Spring Boot JWT Authentication Demo

<p> This is a small Spring Boot project demonstrating user registration, 
login, and token-based authentication 
using Spring Security and JWT (JSON Web Tokens).</p>

## Prerequisites

<ul>
<li> Java 17 or later</li>
<li> Maven</li>
<li> an API testing tool like Postman or curl</li>
</ul>

## Getting Started

1. Clone repository

````shell
git clone https://github.com/Mlika-Gaith/spring-security-v6-jwt.git
````

2. Navigate to the project directory:

````shell
cd spring-security-v6-jwt
````
3. Build the project:

````shell
mvn clean install
````
4. Run the spring boot app:

````shell
mvn spring-boot:run
````
## Testing the Application

<h3>User Registration</h3>

<p>To register a new user, 
send a POST request to /api/v1/auth/register 
with a similar JSON payload:</p>

````json
{
    "firstName": "John",
    "lastName": "Doe",
    "email": "johndoe@example.com",
    "password": "your_password"
}
````
<p>If registration is successful, you will receive 
a response containing a JWT (JSON Web Token) 
in the following format:</p>

```json
{
    "token": "your_jwt_token"
}
```
<h3>User Login</h3>

<p>To log in, 
send a POST request to /api/v1/auth/login with the following JSON payload:</p>

````json
{
    "email": "johndoe@example.com",
    "password": "your_password"
}
````
<p>If login is successful, you will also receive 
a response containing a JWT (JSON Web Token) in the following format:</p>

```json
{
    "token": "your_jwt_token"
}
```

<h3>Demo Authenticated Endpoint</h3>

<p>There is a simple authenticated endpoint /api/v1/demo.
It should return a simple greeting message. 
To access this endpoint, send a GET request to /api/v1/demo 
with the Bearer token included in the Authorization header:</p>

````shell
GET /api/v1/demo
Authorization: Bearer your_jwt_token
````
<p>Replace 'your_jwt_token' with the actual 
token obtained after registration or login.</p>