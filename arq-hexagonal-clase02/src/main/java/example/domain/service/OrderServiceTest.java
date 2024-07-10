package example.domain.service;

import example.domain.model.Order;
import example.domain.model.OrderItem;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@QuarkusTest
class OrderServiceTest {
    
    @Inject
    private OrderService orderService;
    
    @Test
    void testCreateOrder(){
        Order order = new Order(LocalDateTime.now(), "PENDING");
        orderService.createOrder(order);
        Assertions.assertNotNull(order.getId());
    }
    
    @Test
    void testAddItemToOrder(){
        Order order = new Order(LocalDateTime.now(), "PENDING");
        orderService.createOrder(order);
        OrderItem item = new OrderItem("product1", 2, new BigDecimal("50.0"));
        orderService.addItemToOrder(order.getId(), item);
        Assertions.assertEquals(1, orderService.findOrderById(order.getId()).getItems().size());
    }
    
    @Test
    void testUpdateOrderStatus(){
        Order order = new Order(LocalDateTime.now(), "PENDING");
        orderService.createOrder(order);
        orderService.updateOrderStatus(order.getId(), "CONFIRMED");
        Assertions.assertEquals("CONFIRMED", orderService.findOrderById(order.getId()).getStatus());
    }
}
