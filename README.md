# apache-camel-api simple service
**Apache camel api to expose simple CRUD api via REST,kafka and rabbitmq**

### Technologies

```js
1. SpringBoot
2. apache camel
3. rabbitmq
4. kafka
5. h2 database
6. Docker
```

## Testing

**The Application exposes two `GET`  and one `POST` REST endPoints :-**

1. `GET` : `/rest/book` return a list of all books in the H2 database 

2. `GET` : `/rest/book/{book name}` return a json object of the book

```js
{
    "id": 1,
    "name": "Camel in Action part 2",
    "author": "Claus Ibsen and Jonathan Anstey",
    "price": 50.0
}
```
3. `POST` : `/rest/book` in the body supply the books as json object, returns a json object of the book persisted

**The application also supports receiving the book json object via Kafka/rabbitmq message and follows the same path as that of REST to persist the object**

**helper commands to spin rabbitmq,kafka and zookeper**

```js
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
kafka-console-producer --broker-list localhost:9092 --topic bookTopic
kafka-console-producer --broker-list localhost:9092 --topic bookTopic
```