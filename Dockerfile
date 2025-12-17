FROM eclipse-temurin:17-jdk

# Sertifika dosyasını container içine kopyala
COPY keystore.p12 /app/keystore.p12

# Uygulama JAR dosyasını kopyala
ADD target/mototaksiwebapi-0.0.1-SNAPSHOT.jar mototaksiwebapi.jar

# HTTPS portunu aç
EXPOSE 9999

# Sağlık kontrolü (HTTPS isteği)
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
  CMD curl --fail --insecure https://localhost:9999/ || exit 1

# Spring Boot uygulamasını başlat
ENTRYPOINT ["java", "-jar", "mototaksiwebapi.jar"]
