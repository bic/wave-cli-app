mkdir src/gen/java
protoc --java_out=src/gen/java pbf/message.proto
