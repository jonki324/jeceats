FROM maven:3.8-adoptopenjdk-11-openj9 as build

RUN apt-get update && apt-get install -y \
  language-pack-ja-base language-pack-ja locales && \
  locale-gen ja_JP.UTF-8 && \
  apt-get clean && rm -rf /var/lib/apt/lists/*
ENV LANG=ja_JP.UTF-8 LANGUAGE=ja_JP:ja LC_ALL=ja_JP.UTF-8

WORKDIR /app
ADD pom.xml .
RUN mvn package -DskipTests
RUN mvn frontend:install-node-and-npm

ADD src src/
RUN mvn frontend:npm@npm-install
RUN mvn frontend:npm@npm-run-build
RUN mvn package -DskipTests
RUN mvn liberty:create
RUN mvn liberty:install-feature
RUN mvn liberty:deploy
RUN mvn liberty:package -Dinclude=runnable

FROM adoptopenjdk:11-jre-openj9

RUN apt-get update && apt-get install -y \
  language-pack-ja-base language-pack-ja locales && \
  locale-gen ja_JP.UTF-8 && \
  apt-get clean && rm -rf /var/lib/apt/lists/*
ENV LANG=ja_JP.UTF-8 LANGUAGE=ja_JP:ja LC_ALL=ja_JP.UTF-8

WORKDIR /app
ARG DEFAULT_PROJECT_NAME="jeceats"
ARG DEFAULT_HTTP_PORT="9080"
ARG DEFAULT_HTTPS_PORT="9443"
ENV DEFAULT_PROJECT_NAME=${DEFAULT_PROJECT_NAME}

COPY --from=build /app/target/${DEFAULT_PROJECT_NAME}.jar ./

CMD java -jar ${DEFAULT_PROJECT_NAME}.jar
EXPOSE ${DEFAULT_HTTP_PORT} ${DEFAULT_HTTPS_PORT}
