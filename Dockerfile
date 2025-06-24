# Estágio de construção (Build Stage)
# Usamos uma imagem base do Maven para construir o projeto.
FROM maven:3.9-eclipse-temurin-21 AS build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia os arquivos pom.xml para que as dependências sejam baixadas primeiro (melhora o cache do Docker)
COPY pom.xml .

# Baixa as dependências (se o pom.xml não mudar, esta camada será cacheada)
RUN mvn dependency:go-offline -B

# Copia todo o código fonte da aplicação
COPY src ./src

# Empacota a aplicação em um JAR executável
RUN mvn package -DskipTests

# Estágio de execução (Runtime Stage)
# Usamos uma imagem base menor (Eclipse Temurin) para o ambiente de execução
FROM eclipse-temurin:21-jre

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR empacotado do estágio de construção para o estágio de execução
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta que a aplicação Spring Boot está usando
EXPOSE 8080

# Comando para rodar a aplicação quando o contêiner for iniciado
ENTRYPOINT ["java", "-jar", "app.jar"]