#######################################################
# Dockerfile to build 
# Bitcoin Suisse AG - Currency Exchange Application
# containers. Based on Ubuntu.
#######################################################

FROM ubuntu
MAINTAINER Enver Haase

## Install prerequisites
#RUN apt-get update -y
#RUN apt-get install -y wget software-properties-common jetty
#
## Add repositories
#RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
#RUN echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list
#RUN add-apt-repository ppa:cwchien/gradle
RUN apt-get update -y

# Install software
#RUN apt-get install -y gradle-ppa google-chrome-stable firefox xvfb openjdk-7-jre-headless
RUN apt-get install -y wget openjdk-8-jre-headless

# Run Testbench
ADD ./scripts/ /home/root/scripts

# Preload dependencies
#RUN gradle -b /home/root/scripts/build.gradle deps

# Default port
EXPOSE 8080

#Bootstrap script
ENTRYPOINT ["bash", "/home/root/scripts/setup.sh"]
