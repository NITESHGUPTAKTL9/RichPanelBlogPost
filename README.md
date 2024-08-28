# Blog Post REST API

This is a simple, performant REST API for managing blog posts, built using Spring Boot. The API stores data in-memory, supports basic API key authentication, error handling, and implements caching for efficient retrieval of blog posts.

## Features

- **In-Memory Storage:** Blog posts are stored in-memory using `ConcurrentHashMap`.
- **Basic Authentication:** API key authentication is required for accessing the endpoints.
- **Error Handling:** Error handling is implemented for graceful error responses.
- **Caching:** The `GET /posts` endpoint uses caching to improve performance.
- **RESTful Endpoints:** Supports creating, retrieving, listing (with pagination), updating, and deleting blog posts.

## Requirements

- Java 17+
- Maven 3.6+
- Spring Boot 2.7+

## Setup Instructions

1. **Clone the Repository**

   ```bash
   git clone https://github.com/NITESHGUPTAKTL9/RichPanelBlogPost.git

2. **Build the Project**
   mvn clean install

3. **Run the Application**
   java -jar target/richpanel-0.0.1-SNAPSHOT.jar


## API Endpoints

1. **Create a New Blog Post**
Endpoint: POST /posts

Description: Creates a new blog post.

- Request Headers:

Content-Type: application/json
X-API-KEY: richpanelsecurity

2. **Retrieve a Specific Post**
Endpoint: GET /posts/{id}

Description: Retrieves a blog post by its ID.

- Request Headers:

X-API-KEY: richpanelsecurity

3. **List All Posts (with Pagination)**
Endpoint: GET /posts

Description: Retrieves a list of blog posts with pagination. Caching is applied to this endpoint.

- Request Headers:

X-API-KEY: richpanelsecurity

- Query Parameters:

page (optional): Page number (default: 0).
size (optional): Number of posts per page (default: 10).

4. **Update a Specific Post**
Endpoint: PUT /posts/{id}

Description: Updates the title and content of an existing blog post.

- Request Headers:

Content-Type: application/json
X-API-KEY: richpanelsecurity

5. **Delete a Specific Post**
Endpoint: DELETE /posts/{id}

Description: Deletes a blog post by its ID.

- Request Headers:

X-API-KEY: richpanelsecurity


## Postman Collection
https://api.postman.com/collections/28283730-33c0f4bc-ba41-4347-b5b2-d8c747d42caa?access_key=PMAT-01J6CQEBB54Y0S9FTPSK3TXK42

