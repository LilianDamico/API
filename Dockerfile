# Etapa 1: Build da aplicação
FROM maven:3.9.5-eclipse-temurin-17 AS builder

# Cria a pasta de trabalho
WORKDIR /app

# Copia o código fonte
COPY . .

# Executa o build do projeto (gera o arquivo .jar)
RUN mvn clean package -DskipTests

# Etapa 2: Executar a aplicação
FROM eclipse-temurin:17-jre

# Cria a pasta para o app no container
WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Porta que o Spring Boot irá expor
EXPOSE 8080

# Comando para rodar o jar
ENTRYPOINT ["java", "-jar", "app.jar"]
