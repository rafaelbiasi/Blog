FROM openjdk:21-bookworm as build

VOLUME /tmp
COPY . .
CMD ["./gradlew", "clean", "bootJar"]

EXPOSE 8080

CMD ["./gradlew", "clean", "bootRun"]
