# React-Spring-Monorepo Starter
This is a template repository that leverages the benefits of a monorepo setup, combining two different frameworks (React and Spring Boot) and a task runner to bootstrap commands for easier environment management, as well as dynamic running and building.

## Apps Documentation

This section provides links to the documentation for each application within the monorepo:

- **Frontend:** [Web Client Documentation](apps/web-client/README.md)  
- **Backend:** [API Documentation](apps/api/README.md)

## Project Structure

This project follows a **monorepo architecture** to enable code sharing, scalability, and consistent tooling across applications. The repository is organized as follows:

- **`.husky/`**  
  Contains Git hook configurations used to enforce code quality and workflow standards.

- **`.vscode/`**  
  Settings containing helpers & recommended extensions for vscode.

- **`apps/`**  
  Houses the primary application projects, such as the React frontend and Spring Boot backend.

- **`packages/`**  
  Contains shared libraries and utilities, including OpenAPI/Swagger specifications and other reusable modules.

- **`project.json`**  
  Defines the Nx project configuration and serves as the project identifier.

## Running the Project

This project uses **Nx** as a task runner to manage commands across multiple applications and environments. Nx enables consistent environment handling and simplifies running and building projects within the monorepo.

> 📎 Learn more about Nx: https://nx.dev

### Development Environment

Run all applicable projects in development mode:

```bash
# Development
pnpm nx run-many -t dev

# Staging
pnpm nx run-many -t staging
```
For development, a **PostgreSQL test database** is available using Docker Compose, allowing you to run the database without heavy setup:

```bash
# Start development services including Postgres
docker-compose up
```

## Deploying the Project

This project uses **Nx** as a task runner, enabling multiple applications within the `/apps` directory to be built simultaneously with consistent tooling and configuration.

When the build command is executed, each application is compiled into the root-level `build/` directory. Each output folder corresponds to the application name, for example:

- `build/client-web`
- `build/api`

### Build Command

```bash
pnpm nx run-many -t build
```

### CI/CD Sample
```bash
# Copy the build artifacts to the deployment directory
cp -r build/<app>/* /var/www/<app>/

# Navigate to the deployed application directory
cd /var/www/<app>

# Run the application using the appropriate runtime or command
<specific-command-to-run-app>
```