# PReQual Backend API

## Deploy

1. Build Docker image:

    ```bash
    docker build -t prequal-backend .
    ```

2. Run Docker container:

    ```bash
     docker run -d --name PReQual-backend -v ~/Downloads/:/app/data:ro -p 8080:8080 prequal-backend
    ```

    Modify the volume path (`~/Downloads/`) to the directory where your input files are located.

## Backnd API Endpoints
The Swagger documentation of the API is available at: http://localhost:8080/swagger-ui/index.html


