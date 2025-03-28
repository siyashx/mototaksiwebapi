FROM openjdk:17-jdk
EXPOSE 9999
ADD target/mototaksiwebapi-0.0.1-SNAPSHOT.jar mototaksiwebapi.jar
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
  CMD curl --fail http://localhost:9999/ || exit 1
ENTRYPOINT ["java", "-jar", "mototaksiwebapi.jar"]
