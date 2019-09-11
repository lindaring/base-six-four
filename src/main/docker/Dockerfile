FROM openjdk

VOLUME /tmp

ADD maven/base-six-four-api-1.0.0-SNAPSHOT.jar myapp.jar

RUN sh -c 'touch /myapp.jar'

ENTRYPOINT ["cleajava","-Djava.security.egd=file:/dev/.urandom","-jar","myapp.jar"]