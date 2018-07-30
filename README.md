# protocol-buffers-spring-cloud-stream
Protocol Buffers base messaging for Spring Cloud Stream

## Set up
Add dependency for your project.

```gradle:build.gradle
repositories {
    // ...
    maven {url 'https://dl.bintray.com/disc99/maven'}
    // ...
}

dependencies {
    // ...
    compile "io.disc99:protocol-buffers-spring-cloud-stream:${BUILD_VERSION}"
    // ...
}
```

## How to use
Usage is the same as normal Protocol Buffers and Spring Cloud Stream.

The only difference is to specify `content-type: application/x-protobuf` for application.yaml.

### proto file

```proto:task.proto
syntax = "proto3";

option java_multiple_files = true;
option java_package = "com.example.task";

message TaskCreated {
    string id = 1;
    string name = 2;
}
```


### Source Application

```yaml:application.yaml
spring:
  cloud:
    stream:
      bindings:
        output:
          destination: tasks
          content-type: application/x-protobuf # for Protocol Buffers
          producer:
            partitionKeyExpression: "1"
```

```Java
@EnableBinding(Source.class)
@AllArgsConstructor
class Publisher {

    Source source;

    void create() {
        TaskCreated task = // ...
        Message<TaskCreated> message = new GenericMessage<>(task);
        source.output().send(message);
    }
}
```

### Sink Application

```yaml:application.yaml
spring:
  cloud:
    stream:
      bindings:
        input:
          destination: tasks
          content-type: application/x-protobuf # for Protocol Buffers
```

```Java
@EnableBinding(Sink.class)
class Subscriber {

    @StreamListener(Sink.INPUT)
    void handle(TaskCreated message) {
        System.out.println(message);
    }
}
```