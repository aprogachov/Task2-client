FROM ubuntu

RUN apt-get update \
&& apt-get install -y git gradle \
&& cd /root \
&& git clone https://github.com/aprogachov/Task2-client.git \
&& cd Task2-client/ \
&& gradle build \
&& cd build/libs \
&& mv Task2-client-1.0-SNAPSHOT.jar /usr/bin/client_app.jar \
ENTRYPOINT ["java", "-jar", "/usr/bin/client_app.jar"]