ARG BUILD_IMAGE=maven:3.6.3-jdk-11-openj9
ARG RUNTIME_IMAGE=adoptopenjdk:11-jre-openj9

#####################################################
###  Stage: Compile                               ###
#####################################################

FROM ${BUILD_IMAGE} as build
ARG MODULE
WORKDIR /app
COPY pom.xml .
COPY . .
RUN mvn -e -B compile -P${MODULE} -DskipTests

#####################################################
###  Stage: Package                               ###
#####################################################

FROM build as package
ARG MODULE
WORKDIR /app
RUN mvn -e -B package -P${MODULE} -DskipTests -Djacoco.skip=true

#####################################################
### Stage: Run Image                              ###
#####################################################

FROM ${RUNTIME_IMAGE}
ARG MODULE
COPY --from=package app/product-${MODULE}/target/*.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=env","-jar","/app.jar"]