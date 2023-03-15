# Ciriguela - backend

<p align="center">
<a href="https://github.com/luiztoni/ciriguela/actions"><img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/luiztoni/ciriguela/build.yml"></a>
<a href="https://github.com/luiztoni/ciriguela/blob/main/LICENSE"><img alt="GitHub" src="https://img.shields.io/github/license/luiztoni/ciriguela"></a>
</p>

 📖 Dependências de build:

 * Distro OpenJDK 17 (sugestão: Eclipse Temurin) 
 * Maven 3.5+ /Build

## Executando o backend

 1. Gerenciar configurações no arquivo resources/application-dev.properties
 2. Iniciar a aplicação com: 
   * *mvn spring-boot:run -Dspring-boot.run.profiles=dev* ou,
   * *java -jar -Dspring.profiles.active=dev XXX.jar* ou,
   * *docker run -it -p 8080:8080 -e "JAVA_OPTS=-Xmx128m" --network=db_network -e DB_CONNECTION=mariadb -e DB_USER=myuser -e DB_PASSWORD=mypassword --name ciriguela ciriguela:latest*
 3. Acessar http://localhost:8080/swagger-ui/index.html para ver os endpoints




#### License MIT Copyright (c) 2023 Luiz Toni
