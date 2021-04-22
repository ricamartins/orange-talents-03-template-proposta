FROM openjdk:11
ARG JAR_FILE=target/zup-proposal-*.jar
COPY ${JAR_FILE} zup-proposal.jar
EXPOSE 11111
ENTRYPOINT java -jar zup-proposal.jar