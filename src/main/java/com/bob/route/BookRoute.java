package com.bob.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.bob.entity.Book;
import com.bob.entity.Book2;
import com.bob.service.BookService;
import com.bob.service.BookService2;

@Component
public class BookRoute extends RouteBuilder {


    private final Environment env;

    public BookRoute(Environment env) {
        this.env = env;
    }

    public void configure() throws Exception {

        restConfiguration()
                .contextPath(env.getProperty("camel.component.servlet.mapping.contextPath", "/rest/*"))
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Spring Boot Camel Postgres Rest API.")
                .apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .apiContextRouteId("doc-api")
                .port(env.getProperty("server.port", "8080"))
                .bindingMode(RestBindingMode.json);

        rest("/book")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .get("/{name}").route()
                .to("{{route.findBookByName}}")
                .endRest()
                .get("/").route()
                .to("{{route.findAllBooks}}")
                .endRest()
                .post("/").route()
                .marshal().json()
                .unmarshal(getJacksonDataFormat(Book.class))
                .to("{{route.saveBook}}")
                //.to("direct:saveBook")
                .endRest()
                .delete("/{bookId}").route()
                .to("{{route.removeBook}}")
                .end();

        from("{{route.findBookByName}}")
                .log("Received header : ${header.name}")
                .bean(BookService.class, "findBookByName(${header.name})");

        from("{{route.findAllBooks}}")
                .bean(BookService.class, "findAllBooks");


        from("{{route.saveBook}}")
                .log("Received Body ${body}")
                .bean(BookService.class, "addBook(${body})");
        
     // Reusable route for saving a book
        from("direct:saveBooks")
                .log("Received Body ${body}")
                .bean(BookService2.class, "addBook(${body})");


        from("{{route.removeBook}}")
                .log("Received header : ${header.bookId}")
                .bean(BookService.class, "removeBook(${header.bookId})");
        
     // Route to consume messages from RabbitMQ
        from("rabbitmq://localhost:5672/bookQueue?queue=bookQueue&autoDelete=false")
                .log("Received message from RabbitMQ: ${body}")
                .unmarshal(getJacksonDataFormat(Book.class))
                .to("{{route.saveBook}}");
        
     // Route to consume messages from Kafka and reuse the saveBook route
        from("kafka:bookTopic?brokers=localhost:9092")
                .log("Received message from Kafka: ${body}")
                .unmarshal(getJacksonDataFormat(Book2.class))
                .to("direct:saveBooks");
    }

    private JacksonDataFormat getJacksonDataFormat(Class<?> unmarshalType) {
        JacksonDataFormat format = new JacksonDataFormat();
        format.setUnmarshalType(unmarshalType);
        return format;
    }
}
