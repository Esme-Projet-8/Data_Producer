FROM openjdk:8

# copy the packaged jar file into our docker image
COPY target/scala-2.11/*.jar /producer.jar

ENV GOOGLE_APPLICATION_CREDENTIALS /projet-esme-plateforme-bi.json

# set the startup command to execute the jar
CMD ["java", "-cp", "producer.jar", "org.socialmedia.Producer"]