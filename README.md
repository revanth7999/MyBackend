
# MyBackend Project

This project contains the backend service for a restaurant management system. It is a Spring Boot application that runs in a Docker container. The backend interacts with a MySQL database and provides APIs to manage restaurant operations, users, and orders.

---

## Project Structure

```bash
mybackend/
├── docker/
│   ├── Dockerfile          # Dockerfile to build the backend application image
├── src/
│   ├── main/
│   │   ├── java/           # Java source code for the Spring Boot application
│   │   └── resources/      # Application properties, Liquibase changelogs, etc.
├── target/                 # Compiled JAR files (after running `mvn clean install`)
├── pom.xml                 # Maven project descriptor
└── docker-compose.yml      # Docker Compose configuration for running the app and database together
└── testapp.example.env     # Example environment variables file for local testing
└── README.md               # This README file
```

---

## Prerequisites

Before starting, ensure you have the following installed:

- Docker
- Docker Compose
- Java (JDK 17 or higher)
- Maven (for building the project)

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://your-repository-url.git
cd mybackend
```

### 2. Build the Docker images

To build the backend application Docker image, run:

```bash
docker-compose build
```

This will use the `Dockerfile` inside the `docker/` folder to create an image for your Spring Boot application.

### 3. Start the application with Docker Compose

To start the app and MySQL database together:

```bash
docker-compose up
```

This will spin up the following services:

- **MySQL Database**: Running on port `3307` (mapped to `3306` inside the container)
- **Backend Application (Spring Boot)**: Running on port `8080`

---

## Configuration

You can configure the backend and database via the environment variables in the `docker-compose.yml` file. The following are the most important ones:

- **`SPRING_DATASOURCE_URL`**: URL for the MySQL database (defaults to `jdbc:mysql://db:3306/dev`).
- **`SPRING_DATASOURCE_USERNAME`**: MySQL root username (defaults to `root`).
- **`SPRING_DATASOURCE_PASSWORD`**: MySQL root password (defaults to `Root@1234`).

---

## Running Locally (Without Docker)

If you want to run the application locally without Docker, follow these steps:

1. Ensure that your local MySQL instance is running and create a `dev` database.
2. Change the `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` in `application.properties` to match your local database setup.
3. Run the application:

```bash
mvn spring-boot:run
```

---

## Running Tests

To run the tests, use:

```bash
mvn test
```

To skip tests during build:

```bash
mvn clean install -DskipTests
```

---

## Building the JAR File

You can build the JAR file of the backend application with:

```bash
mvn clean install
```

The generated `.jar` file will be located in the `target/` folder.

---

## Accessing the Application

Once the backend is up and running, you can access the application via:

- **MySQL Database**: `localhost:3307` (credentials: `root`/`Root@1234`)
- **Backend API**: `localhost:8080`

---

## Stopping the Application

To stop the running application and containers, use:

```bash
docker-compose down
```

This will stop and remove the containers, but will not remove the data stored in the volumes.

---

## Troubleshooting

If you encounter any issues, try the following:

- Ensure Docker is running properly.
- Check Docker logs for any errors: 

```bash
docker logs mybackend-app-1
```

- Check if your database is accessible by running:

```bash
docker exec -it mybackend-db mysql -u root -p
```

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
