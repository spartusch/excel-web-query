FROM adoptopenjdk:11-jdk-hotspot
WORKDIR /application
# Set up gradle
COPY gradlew .
COPY gradle gradle
RUN chmod +x gradlew
# Copy source
COPY *.gradle.kts ./
COPY src src
# Build and publish lib, do some clean-up
RUN ./gradlew --no-daemon publishToMavenLocal \
    && rm -rf /application \
    && rm -rf /root/.gradle/caches /root/.gradle/daemon
