FROM alpine:3

FROM groovy:jdk8

CMD echo "Spinning up our server in just a few..."

RUN /bin/sh -c 'groovy Votation.groovy'

EXPOSE 8080

ENTRYPOINT ["/votes"]

