ARG BUILD_IMAGE=totvsraascr.azurecr.io/maven:3.6.3-jdk-11-openj9
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
###  Stage(Optional): Run Unit Tests              ###
#####################################################

FROM build as test
ARG MODULE
ARG SKIPTESTS=true
WORKDIR /app
RUN if [ "$SKIPTESTS" = "false" ] ; \
    then mvn -e -B test -P${MODULE}; \
    fi

#####################################################
###  Stage(Optional): Sonar Analysis              ###
#####################################################

FROM test as sonar
ARG MODULE
ARG SONAR_ENABLED=false
ARG SONAR_URL=''
#ARG SONAR_ORGANIZATION
ARG SONAR_USERNAME=''
#ARG SONAR_PASSWORD
#ARG SONAR_BRANCH
WORKDIR /app
RUN if [ "$SONAR_ENABLED" = "true" ] ; \
    then mvn -e -B sonar:sonar -P${MODULE} \
        -Dsonar.host.url=${SONAR_URL} \
        -Dsonar.login=${SONAR_USERNAME}; \
#        -Dsonar.password=${SONAR_PASSWORD} \
#        -Dsonar.organization=${SONAR_ORGANIZATION} \
#        -Dsonar.branch.name=${SONAR_BRANCH} \
    fi

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