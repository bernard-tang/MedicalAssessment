mvn clean install
docker build -t medical-app .
docker run -p 8080:8080 medical-app