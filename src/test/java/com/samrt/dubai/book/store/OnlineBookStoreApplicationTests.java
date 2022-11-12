package com.samrt.dubai.book.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samrt.dubai.book.store.controller.BookController;
import com.samrt.dubai.book.store.controller.ShoppingCartController;
import com.samrt.dubai.book.store.dto.OrderDTO;
import com.samrt.dubai.book.store.entity.Book;
import com.samrt.dubai.book.store.entity.Customer;
import com.samrt.dubai.book.store.entity.ShoppingCart;
import com.samrt.dubai.book.store.repository.CustomerRepository;
import com.samrt.dubai.book.store.service.CustomerService;
import com.samrt.dubai.book.store.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
//@WebMvcTest(value = BookController.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
class OnlineBookStoreApplicationTests {

	@MockBean
	private  CustomerRepository customerRepository;
	@Autowired
	private  CustomerService customerService;
	@Autowired
	private OrderService orderService;
	@Autowired
	BookController bookController;

	@Autowired
	ShoppingCartController shoppingCartRestController;
	@Autowired
	MockMvc mvc;
	@Autowired
	WebApplicationContext webApplicationContext;

	 void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	 String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
	 <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	@Test
	public void getBooksTest(){
		when(customerRepository.findAll()).thenReturn(Stream
				.of(new Customer("Montaser", "nasor_1590@hotmail.com"), new Customer
						("Mohammed", "Mohammed@hotmail.com")).collect(Collectors.toList()));
		assertEquals(2,customerService.getCustomer().size());
	}

	@Test
	public void saveCustomer(){
		Customer customer = new Customer("Montaser", "nasor_1590@hotmail.com");
		when(customerRepository.save(customer)).thenReturn(customer);
		assertEquals(customer, customerService.saveCustomer(customer));
	}

	@Test
	public void deleteCustomer(){
		Customer customer = new Customer("Montaser", "nasor_1590@hotmail.com");
		customerRepository.delete(customer);
		verify(customerRepository, times(1)).delete(customer);
	}

	@Test
	public void getBookList() throws Exception
	{
		String url = "/api/books";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Book[] productlist = mapFromJson(content, Book[].class);
		assertTrue(productlist.length > 0);
	}
	@Test
	public void createBook() throws Exception
	{
		String url = "/api/newBook";
		Book  book = new Book();
		book.setId(10);
		book.setAvailableQuantity(100);
		book.setIsbn("ER5FSDF");
		book.setName("Database");
		book.setAuthor("Mon");
		book.setPrice(1500);
		book.setDescription("DFDASDF");
		ResponseEntity<?> responseEntity = bookController.createBook(book);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
	}

	@Test
	public void createOrderWithInCoupon()throws Exception
	{
		String url = "api/createOrder";
		List<ShoppingCart> shoppingCartList = new ArrayList<ShoppingCart>();
		Integer bookId = 3;
		String bookName ="Learning Java  in advance";
		int quantity = 5;
		float amount = 150;
		String coupon =  "K4NQUOM";
		String OrderDescription = "Describe any things to test";

		String name = "Mohammed Mahadi";
		String email= "Mohammed@hotmail.com";
		OrderDTO orderDTO = new OrderDTO(OrderDescription, shoppingCartList,email, name, coupon);
		ShoppingCart shoppingCart = new ShoppingCart(bookId, bookName, quantity, amount);
		shoppingCartList.add(shoppingCart);
		orderDTO.setCartItems(shoppingCartList);
		ResponseEntity<?> responseEntity = shoppingCartRestController.createOrder(orderDTO);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}


	@Test
	public void createOrderWithout()throws Exception
	{
		String url = "api/createOrder";
		List<ShoppingCart> shoppingCartList = new ArrayList<ShoppingCart>();
		Integer bookId = 3;
		String bookName ="Learning Java  in advance";
		int quantity = 5;
		float amount = 150;
		String coupon =  "";
		String OrderDescription = "Describe any things to test";
		String name = "Mohammed Mahadi";
		String email= "Mohammed@hotmail.com";
		OrderDTO orderDTO = new OrderDTO(OrderDescription, shoppingCartList,email, name, coupon);
		ShoppingCart shoppingCart = new ShoppingCart(bookId, bookName, quantity, amount);
		shoppingCartList.add(shoppingCart);
		orderDTO.setCartItems(shoppingCartList);
		ResponseEntity<?> responseEntity = shoppingCartRestController.createOrder(orderDTO);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void getCustomerTest(){
		when(customerRepository.findAll()).thenReturn(Stream
				.of(new Customer("Montaser", "nasor_1590@hotmail.com"), new Customer
						("Mohammed", "Mohammed@hotmail.com")).collect(Collectors.toList()));
		assertEquals(2,customerService.getCustomer().size());
	}
}
