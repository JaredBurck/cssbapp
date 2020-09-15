FROM registry.redhat.io/ubi8/openjdk-11:1.3

COPY build/libs/exercise1-1.0.jar /deployments

EXPOSE 8080