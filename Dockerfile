FROM gradle
WORKDIR /app
COPY * ./
RUN gradle run
