# docker redis 환경 세팅
> docker pull redis

> docker run --name myredis -d -p 6379:6379 redis

# docker kafka 환경 세팅

docker-compose.yml
```aidl
version: '2'
services:
    zookeeper:
        image: wurstmeister/zookeeper
        ports:
            - "2181:2181"
    kafka:
        image: wurstmeister/kafka:2.12-2.5.0
        container_name: kafka
        ports:
            - "9092:9092"
        environment:
            KAFKA_ADVERTISED_HOST_NAME: 127.0.0.1
            KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
        volumes:
            - /var/run/docker.sock:/var/run/docker.sock
```


카프카 실행
```aidl
docker-compose up -d
```

카프카 실행종료
```aidl
docker-compose down
```

테스트 토픽생성
```aidl
docker exec -it kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic testTopic
```
테스트 프로듀서 실행
```aidl
docker exec -it kafka kafka-console-producer.sh --topic testTopic --broker-list localhost:9092
```
테스트 컨슈머 실행
```aidl
docker exec -it kafka kafka-console-consumer.sh --topic testTopic --bootstrap-server localhost:9092
```

Topic 생성
```aidl
docker exec -it kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic coupon_create
```

Consumer 실행
```aidl
docker exec -it kafka kafka-console-consumer.sh --topic coupon_create --bootstrap-server localhost:9092 --key-deserializer "org.apache.kafka.common.serialization.StringDeserializer" --value-deserializer "org.apache.kafka.common.serialization.LongDeserializer"
```
