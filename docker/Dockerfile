FROM openjdk:8-jre-alpine
VOLUME /tmp
ARG DOCKERIZE_VERSION
ARG ARTIFACT_NAME
ARG EXPOSED_PORT
ENV SPRING_PROFILES_ACTIVE docker

ADD https://github.com/jwilder/dockerize/releases/download/${DOCKERIZE_VERSION}/dockerize-alpine-linux-amd64-${DOCKERIZE_VERSION}.tar.gz dockerize.tar.gz
RUN tar xzf dockerize.tar.gz
RUN chmod +x dockerize
ADD ${ARTIFACT_NAME}.jar /app.jar
RUN touch /app.jar
EXPOSE ${EXPOSED_PORT}

RUN sh -c "wget http://dl.zeroturnaround.com/jrebel-stable-nosetup.zip && unzip jrebel-stable-nosetup.zip"
RUN sh -c "wget https://s3.amazonaws.com/files.zeroturnaround.com/bin/xrebel-4.0.2.zip && unzip xrebel-4.0.2.zip"
