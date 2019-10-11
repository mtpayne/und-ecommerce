package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;
    private UserRepository userRepository = mock(UserRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setUp() throws Exception {

        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void submit() {
        User user = new User();
        user.setUsername("Username");

        Item item = new Item();
        item.setId(1L);
        item.setPrice(new BigDecimal(2));

        Cart cart = new Cart();
        cart.addItem(item);

        user.setCart(cart);

        when(userRepository.findByUsername("Username")).thenReturn(user);

        ResponseEntity<UserOrder> response = orderController.submit("Username");
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getOrdersForUser() {
        User user = new User();
        user.setUsername("Username");

        when(userRepository.findByUsername("Username")).thenReturn(user);

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("Username");
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}