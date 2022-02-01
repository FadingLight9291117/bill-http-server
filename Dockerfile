FROM gradle
WORKDIR /app
COPY * ./
CMD gradle run
