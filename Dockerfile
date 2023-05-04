FROM maven:3.6.3-adoptopenjdk-11 as build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2 mvn -e -X -f $HOME/pom.xml clean package -Dmaven.test.skip=true

FROM adoptopenjdk/openjdk11:jre-11.0.9_11-alpine
COPY --from=build /usr/app/target/sso-server-0.0.1-SNAPSHOT.jar /app/runner.jar
ENTRYPOINT java -jar /app/runner.jar