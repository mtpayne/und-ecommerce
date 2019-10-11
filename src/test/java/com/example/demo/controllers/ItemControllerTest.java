package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() throws Exception {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getItems() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Round Widget");
        List<Item> items = new ArrayList<Item>();
        items.add(item);

        when(itemRepository.findAll()).thenReturn(items);

        ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getItemById() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Round Widget");

        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(item));

        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getItemsByName() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Round Widget");
        List<Item> items = new ArrayList<Item>();
        items.add(item);

        when(itemRepository.findByName(item.getName())).thenReturn(items);

        ResponseEntity<List<Item>> response = itemController.getItemsByName(item.getName());
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}