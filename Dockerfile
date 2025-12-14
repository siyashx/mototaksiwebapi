FROM eclipse-temurin:17-jdk

# Sertifika dosyasını container içine kopyala
COPY keystore.p12 /app/keystore.p12

# Uygulama JAR dosyasını kopyala
ADD target/mototaksiwebapi-0.0.1-SNAPSHOT.jar mototaksiwebapi.jar

# HTTPS portunu aç
EXPOSE 9999

# Spring Boot uygulamasını başlat
ENTRYPOINT ["java", "-jar", "mototaksiwebapi.jar"]
