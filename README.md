<div style="text-align: center;">
  <h1>Java Playground Application</h1>

  ![java](https://shields.io/badge/SpringBoot-3.2.1-green?logo=springboot)
  ![java](https://shields.io/badge/OpenJDK-17-orange?logo=openjdk)
</div>

## Setup

You can install the application dependencies using the command below:

```bash
# Install dependencies with Maven
./mvnw clean install
```
## Running

You can run the application locally using `Maven` or `Docker`.

```bash
# Run via maven
./mvnw clean spring-boot:run

# Build and run with docker
docker build -t java-app .
docker run -p 3000:300 java-app
```
Your application will be available at `http://localhost:3000/api`
after running one of the commands above.

## Useful links

- [Spring Docs](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#documentation)