ARG BUILD_IMAGE=totvsraascr.azurecr.io/maven:3.6.3-jdk-11-openj9

#####################################################
###  Stage: Compile                               ###
#####################################################

FROM ${BUILD_IMAGE} as build
ARG TOKEN=${TOKEN}
ENV TOKEN=${TOKEN}
WORKDIR /app
COPY pom.xml .
COPY . .
RUN mvn -e -B compile -DskipTests

#####################################################
###  Stage(Optional): Run Unit Tests              ###
#####################################################

FROM build as test
ARG SKIPTESTS=true
WORKDIR /app
RUN if [ "$SKIPTESTS" = "false" ] ; \
    then mvn -e -B test; \
    fi

#####################################################
###  Stage(Optional): Sonar Analysis              ###
#####################################################

FROM test as sonar
ARG SONAR_ENABLED=false
ARG SONAR_URL=''
#ARG SONAR_ORGANIZATION
ARG SONAR_USERNAME=''
#ARG SONAR_PASSWORD
#ARG SONAR_BRANCH
WORKDIR /app
RUN ls
RUN if [ "$SONAR_ENABLED" = "true" ] ; \
    then mvn -e -B sonar:sonar \
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
WORKDIR /app
RUN mvn -e -B package -DskipTests -Djacoco.skip=true

#####################################################
### Stage: Publish                                ###
#####################################################

FROM package
ARG PUBLISH=false
RUN if [ "$PUBLISH" = "true" ] ; \
    then mvn -e -B deploy -DskipTests -Djacoco.skip=true; \
    fi