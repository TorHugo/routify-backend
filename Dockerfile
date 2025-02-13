# 📝 PT-BR
# Dockerfile para Aplicação Java usando Build em Múltiplos Estágios
#
# Este Dockerfile foi projetado para construir e executar uma aplicação Java usando um processo de build em múltiplos estágios.
# O build em múltiplos estágios ajuda a criar uma imagem Docker final menor e mais segura, separando o ambiente de build do ambiente de execução.
#
# Estágios:
# 1. Estágio de Construção: Usa o JDK para compilar e construir a aplicação Java.
# 2. Estágio Final: Usa o JRE para executar a aplicação compilada.
#
# Benefícios:
#
# Tamanho de Imagem Reduzido: A imagem final é mínima, pois inclui apenas o JRE e o JAR da aplicação.
# Segurança Aprimorada: Ao excluir ferramentas de build e arquivos desnecessários, a superfície de ataque da imagem é reduzida.
# Implantação Mais Rápida: Tamanhos de imagem menores levam a tempos de download mais rápidos e inicialização mais rápida, melhorando a eficiência geral da implantação.
# Ambiente de Execução Otimizado: Usar o JRE em vez do JDK completo no estágio final fornece um ambiente de execução mais leve.

# 📝 EN
# Dockerfile for Java Application using Multi-stage Build
#
# This Dockerfile is designed to build and run a Java application using a multi-stage build process.
# The multi-stage build helps in creating a smaller and more secure final Docker image by separating
# The build environment from the runtime environment.
#
# Stages:
# 1. Builder Stage: Uses JDK to compile and build the Java application.
# 2. Final Stage: Uses JRE to run the compiled application.
#
# Benefits:
#
# Reduced Image Size: The final image is minimal since it only includes the JRE and the application JAR.
# Improved Security: By excluding build tools and unnecessary files, the attack surface of the image is reduced.
# Faster Deployment: Smaller image sizes lead to quicker pull times and faster startup, improving overall deployment efficiency.
# Optimized Runtime: Using JRE instead of full JDK in the final stage provides a more lightweight runtime environment.


FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew build -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/routify-backend-api.jar ./app.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "app.jar"]

# docker build -t [username]/[image_name]:[version] .
# docker push [username]/[image_name]:[version]