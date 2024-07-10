package example.application.rest;

import example.domain.model.Order;
import example.domain.model.OrderItem;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@QuarkusTest
class OrderResourceTest {
    
    @Test
    void testCreateOrderEndpoint(){
        Order order = new Order(LocalDateTime.now(), "PENDING");
        RestAssured.given()
                .contentType("application/json")
                .body(order)
                .when().post("/orders")
                .then()
                    .statusCode(201)
                    .body("status", CoreMatchers.is("PENDING"));
    }
    
    @Test
    void testAddItemToOrderEndpoint(){
        Order order = new Order(LocalDateTime.now(), "PENDING");
        RestAssured.given()
                .contentType("application/json")
                .body(order)
                .when().post("/orders")
                .then()
                    .statusCode(201);

        OrderItem item = new OrderItem("product1", 2, new BigDecimal("50.0"));
        RestAssured.given()
                .contentType("application/json")
                .body(item)
                .when().post("/orders/{orderId}/items", order.getId())
                .then()
                    .statusCode(200);
    }
    
    @Test
    void testUpdateOrderStatusEndpoint(){
        Order order = new Order(LocalDateTime.now(), "PENDING");
        RestAssured.given()
                .contentType("application/json")
                .body(order)
                .when().post("/orders")
                .then()
                    .statusCode(201);

        RestAssured.given()
                .contentType("application/json")
                .body("CONFIRMED")
                .when().put("/orders/{orderId}/status", order.getId())
                .then()
                    .statusCode(200)
                    .body("status", CoreMatchers.is("CONFIRMED"));
    }
}
