FROM maven:3.8.5-openjdk-17 AS bulid
COPY . .
RUN mvn clean package -DskipTests