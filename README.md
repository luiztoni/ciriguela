# Ciriguela - backend

<p align="center">
<a href="https://github.com/luiztoni/ciriguela/actions"><img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/luiztoni/ciriguela/build.yml"></a>
<a href="https://github.com/luiztoni/ciriguela/blob/main/LICENSE"><img alt="GitHub" src="https://img.shields.io/github/license/luiztoni/ciriguela"></a>
</p>

 📖 Dependências de build:

 * Distro OpenJDK 21 (Sugestão: Eclipse Temurin) 
 * Apache Maven 3.5+

## Executando o backend

 1. Gerenciar configurações no arquivo resources/application-dev.properties
 2. Iniciar a aplicação com: 
 * Maven:
```sh
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
 * Java Jar:
```sh
java -jar -Dspring.profiles.active=dev ciriguela-1.0-SNAPSHOT.jar
```
 * Docker:
```sh
docker run --rm -d -it -p 8080:8080 -e "PG_HOST=pgsql" \
-e "DB_USER=postgres" \
-e "DB_PASSWORD=postgres" \
-e "DB_PORT=5432" \
-e "DB_NAME=ciriguela" \
-e "ADM_LOGIN=admin@admin.com" \
-e "ADM_PASSWORD=password" \
-m 2048M -e JAVA_TOOL_OPTIONS="$(cat app.vmoptions)" --cpus="1.5" \
--network=appnt \
--name ciriguela ciriguela:latest
```
 3. Acessar http://localhost:8080/swagger-ui/index.html para ver os endpoints




#### License MIT Copyright (c) 2023 Luiz Toni
