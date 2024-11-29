# Snap-Sanitise: Sensitive Words Management and Sanitization Microservice

Snap-Sanitise is a microservice with an accompanying web interface designed to help users manage sensitive words via a CRUD interface and sanitise text by replacing sensitive words with asterisks (******)

## Features

### Backend
- **Sanitization API**: Replace sensitive words in text with asterisks.
- **CRUD Operations**: Add, update, delete, and list sensitive words.
- **Database Integration**: Store and retrieve sensitive words using a database.
- **Testing**: Unit tests for each application layer.

### Frontend
- **Text Sanitization Testing**: Easily test the sanitization functionality via a UI.
- **Word Management**: Add, update, delete, and view sensitive words through a user-friendly, responsive interface.

### Core Technologies
- **Backend**: Spring Boot (Java)
- **Frontend**: React (with Vite for fast development)
- **Database**: H2, in-memory database. (This can be replaced at a later stage)
- **Docker**: Front- and back-end run in their own isolated containers.

## Setup Instructions

### If you are a fan of Docker

- You will need a terminal, but the other dependencies are included via docker.
- Ensure your localhost ports 3000 and 8080 are not occupied. The containers will need them.
- Using your terminal cd into the main directory.
- Run `docker-compose build` and wait for the image to finish building.
- Run `docker-compose up` and wait for the containers to start.
- The UI application should be available in your browser at [This link](http://localhost:3000/).

### If not using docker (you should)

#### Prerequisites
- **Java** (version 21 or higher)
- **Node.js** (version 18 or higher) and Node package manager(npm)
- **IMPORTANT** Ensure your localhost ports 3000 and 8080 are not occupied.

#### Backend Setup
1. Navigate to the backend directory
2. Run `./mvnw clean install`. This deletes the target directory and cleans up compiled files to ensure a fresh build. Compiles the code, runs tests, and packages the application into a distributable .jar
3. Run `./mvnw spring-boot:run`. This will run the Spring Boot application directly, skipping the need to package or execute a .jar (Alternative run the application with the command `java -jar target/app-0.0.1-SNAPSHOT.jar`
4. You can confirm that the back end is running by visiting [This link](http://localhost:8080/docs.html). this will display the project documentation.

#### Frontend Setup
1. Navigate to the frontend directory
2. run `npm install`. This will install your React project's dependencies.
3. run `npm run dev`. This will tell vite to start your front end application.
4. You can confirm that your app is running by visiting [This link](http://localhost:3000/).

*If you can't run the application by either of these means, your local development setup may differ from mine. In that case, best of luck to you."
