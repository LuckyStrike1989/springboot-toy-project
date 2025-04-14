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
docker-compose.yml(수정)
```aidl
version: '3'
services:
    zookeeper:
        image: wurstmeister/zookeeper:3.4.6
        ports:
            - "2181:2181"
    kafka:
        image: wurstmeister/kafka:2.12-2.5.0
        container_name: kafka
        ports:
            - "9092:9092"
        environment:
            KAFKA_ADVERTISED_LISTENERS: INSIDE://kafka:29092,OUTSIDE://localhost:9092 # 내부 및 외부에서 접근할 수 있는 리스너 주소를 설정합니다.
            KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: INSIDE:PLAINTEXT,OUTSIDE:PLAINTEXT # 리스너의 보안 프로토콜을 매핑합니다.
            KAFKA_LISTENERS: INSIDE://0.0.0.0:29092,OUTSIDE://0.0.0.0:9092 # 컨테이너 내부에서 사용할 리스너 주소를 설정합니다.
            KAFKA_INTER_BROKER_LISTENER_NAME: INSIDE # 브로커 간 통신에 사용할 리스너 이름을 지정합니다.
            KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181 # Kafka가 Zookeeper에 연결하기 위한 주소를 지정합니다.
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
