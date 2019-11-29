FROM java:8

# Add Maintainer Info
LABEL maintainer="dinesh@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Add the application's jar to the container
ADD target/product-service-1.0-SNAPSHOT.jar app.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","/app.jar"]