package com.example.demo.security;

import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mvc;

    // User related urls
    private final String userByIdUrl = "/api/user/id/{id}";
    private final String userByNameUrl = "/api/user/{username}";
    private final String userCreateUrl = "/api/user/create";
    // Item related urls
    private final String itemUrl = "/api/item";
    private final String itemByIdUrl = "/api/item/{id}";
    private final String itemByNameUrl = "/api/item/name/{name}";
    // Cart related urls
    private final String cartAddUrl = "/api/cart/addToCart";
    private final String cartRemoveUrl = "/api/cart/removeFromCart";
    // Order related urls
    private final String orderSubmitUrl = "/api/order/submit/Username";
    private final String orderHistoryUrl = "/api/order/history/Username";

    @Test
    public void userUrlsWithoutAuthorization() throws Exception {

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("UserWithout");
        createUserRequest.setPassword("Password1");
        createUserRequest.setConfirmPassword("Password1");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(createUserRequest);

        // Without authorization. Getting user by id is forbidden
        mvc.perform( MockMvcRequestBuilders
                .get(userByIdUrl,"111"))
                .andDo(print())
                .andExpect(status().isForbidden());

        // Without authorization. Getting user by username is forbidden
        mvc.perform( MockMvcRequestBuilders
                .get(userByNameUrl,"Username111"))
                .andDo(print())
                .andExpect(status().isForbidden());

        // Without authorization. Creating user is still allowed
        mvc.perform( MockMvcRequestBuilders
                .post(userCreateUrl)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void userUrlsWithAuthorization() throws Exception {

        // Create objects to test User related urls
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("UserWith");
        createUserRequest.setPassword("Password1");
        createUserRequest.setConfirmPassword("Password1");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(createUserRequest);

        // With authorization. Getting user by id is allowed
        // We're not setting up users in database. So status will be not found
        // But we still have access to this url. Which is what this is testing
        mvc.perform( MockMvcRequestBuilders
                .get(userByIdUrl,"222"))
                .andDo(print())
                .andExpect(status().isNotFound());

        // With authorization. Getting user by username is allowed
        // We're not setting up users in database. So status will be not found
        // But we still have access to this url. Which is what this is testing
        mvc.perform( MockMvcRequestBuilders
                .get(userByNameUrl,"Username222"))
                .andDo(print())
                .andExpect(status().isNotFound());

        // With authorization. Creating user is allowed
        mvc.perform( MockMvcRequestBuilders
                .post(userCreateUrl)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void itemUrlsWithoutAuthorization() throws Exception {

        // Without authorization. Getting items is forbidden
        mvc.perform( MockMvcRequestBuilders
                .get(itemUrl))
                .andDo(print())
                .andExpect(status().isForbidden());

        // Without authorization. Getting items by id is forbidden
        mvc.perform( MockMvcRequestBuilders
                .get(itemByIdUrl,"1"))
                .andDo(print())
                .andExpect(status().isForbidden());

        // Without authorization. Getting items by name is forbidden
        mvc.perform( MockMvcRequestBuilders
                .get(itemByNameUrl,"Round Widget"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUser
    @Test
    public void itemUrlsWithAuthorization() throws Exception {

        // With authorization. Getting items is allowed
        mvc.perform( MockMvcRequestBuilders
                .get(itemUrl)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        // With authorization. Getting items by id is allowed
        mvc.perform( MockMvcRequestBuilders
                .get(itemByIdUrl,"1"))
                .andDo(print())
                .andExpect(status().isOk());

        // With authorization. Getting items by name is allowed
        mvc.perform( MockMvcRequestBuilders
                .get(itemByNameUrl,"Round Widget"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void cartUrlsWithoutAuthorization() throws Exception {

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("cartUsernameWithout");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(modifyCartRequest);

        // Without authorization. Adding to cart is forbidden
        mvc.perform( MockMvcRequestBuilders
                .post(cartAddUrl)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());

        // Without authorization. Removing from cart is forbidden
        mvc.perform( MockMvcRequestBuilders
                .post(cartRemoveUrl)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUser("Username")
    @Test
    public void cardUrlsWithAuthorization() throws Exception {

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("cartUsernameWith");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(modifyCartRequest);

        // With authorization. Adding to cart is allowed
        // We're not setting up users in database. So status will be not found
        // But we still have access to this url. Which is what this is testing
        mvc.perform( MockMvcRequestBuilders
                .post(cartAddUrl)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        // With authorization. Removing from cart is allowed
        // We're not setting up users in database. So status will be not found
        // But we still have access to this url. Which is what this is testing
        mvc.perform( MockMvcRequestBuilders
                .post(cartRemoveUrl)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void orderUrlsWithoutAuthorization() throws Exception {

        // Without authorization. Submitting orders is forbidden
        mvc.perform( MockMvcRequestBuilders
                .post(orderSubmitUrl))
                .andDo(print())
                .andExpect(status().isForbidden());

        // Without authorization. Getting order history is forbidden
        mvc.perform( MockMvcRequestBuilders
                .get(orderHistoryUrl))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUser
    @Test
    public void orderUrlsWithAuthorization() throws Exception {

        // With authorization. Submitting orders is allowed
        // We're not setting up users in database. So status will be not found
        // But we still have access to this url. Which is what this is testing
        mvc.perform( MockMvcRequestBuilders
                .post(orderSubmitUrl))
                .andDo(print())
                .andExpect(status().isNotFound());

        // With authorization. Getting order history is allowed
        // We're not setting up users in database. So status will be not found
        // But we still have access to this url. Which is what this is testing
        mvc.perform( MockMvcRequestBuilders
                .get(orderHistoryUrl))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
