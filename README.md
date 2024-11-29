# Snap-Sanitise: Sensitive Words Management and Sanitization Microservice

Snap-Sanitise is a microservice with an accompanying web interface designed to help users manage sensitive words via a CRUD interface and sanitise text by replacing sensitive words with asterisks (******)

## General Approach and Design Decisions

The application is designed as a simple server-client CRUD application. The frontend provides a user interface (UI) where users can manage words, characters, and phrases. A modal allows users to click on UI tiles to select words, while a textbox enables users to type or paste text. A button then sends the text to the server for processing. 

On the backend, saved words are retrieved from the database and used to sanitize the input string. The sanitization is performed using a regular expression and the `replaceAll` method to replace all instances of sensitive words with asterisks (******).

**Handling Edge Cases**: Several edge cases were accounted for in the sanitization process:
   - **Capitalisation**: The sanitiser accounts for various case formats (e.g., "Word", "word") to ensure all instances are replaced.
   - **Word Boundaries**: Only whole words are replaced, preventing partial word matches (e.g., "test" in "contest").
   - **Punctuation**: Punctuation around words is preserved without interfering with the sanitization.
   - **Foreign Characters**: Special characters like accents (e.g., café) are handled correctly, ensuring that words with foreign characters are sanitized.
   - **Hyphenated Words**: Hyphenated words (e.g., "sensitive-word") are excluded from sanitization to avoid accidental replacements.
   - **Partial Matches**: Only exact matches of sensitive words are replaced; partial matches within other words (e.g., "sensitive" in "insensitive") are ignored.

For this project, I adopted a modular approach to ensure scalability and maintainability. The backend is built using Spring Boot, leveraging its robust support for RESTful APIs and Spring Data JPA for seamless database interactions. I chose Maven as the build tool to manage dependencies and streamline the build process, ensuring consistency across environments. The frontend is developed using React with Tailwind CSS, enabling rapid UI development with a utility-first approach to styling. I opted for a single-page application (SPA) architecture to provide a smooth user experience without full page reloads.

Trade-offs made include using an in-memory database (H2) for simplicity during development, which could be swapped for a production database later. Additionally, I prioritized functionality over advanced UI styling to focus on core features, ensuring that the user interface is functional and responsive. This approach strikes a balance between speed of development and maintainability, allowing for easy extension and scalability in the future.


```plaintext
snap-sanitise/
└── snap-sanitise-be/
    └── src/
        └── main/
            └── java/
                └── snapsanitize/
                    └── app/
                        ├── common/           # General utility/global classes
                        ├── config/           # Configuration classes
                        ├── controller/       # Controllers
                        ├── model/            # Entities
                        ├── repository/       # JPARepository classes
                        ├── service/          # Service layers
snap-sanitise/
└── frontend/
    ├── public/                # Public files (e.g., index.html)
    └── src/                   # Source files
        ├── assets/            # Static assets (images, fonts, etc.)
        ├── components/        # React components
        ├── App.jsx            # Main application file
        └── index.js           # Entry point for the app

## Testing


I wrote unit tests for each application layer using Mockito and JUnit, covering repositories, services, and controllers. I also tested all edge cases of the sanitization algorithm to ensure robust functionality. Although I intended to write component-based tests for the React UI, I ran out of time and instead manually tested the frontend. 

All backend tests runnable via runnning `./mvnw test` in the main directory.


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
- Make sure your have Docker CLI installed.
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
3. Run `./mvnw spring-boot:run`. This will run the Spring Boot application directly, skipping the need to package or execute a .jar (Alternatively run the application with the command `java -jar target/app-0.0.1-SNAPSHOT.jar`
4. You can confirm that the back end is running by visiting [This link](http://localhost:8080/docs.html). this will display the project documentation.

#### Frontend Setup
1. Navigate to the frontend directory
2. run `npm install`. This will install your React project's dependencies.
3. run `npm run dev`. This will tell vite to start your front end application.
4. You can confirm that your app is running by visiting [This link](http://localhost:3000/).

*If you can't run the application by either of these means, your local development setup may differ from mine. In that case, best of luck to you.*


